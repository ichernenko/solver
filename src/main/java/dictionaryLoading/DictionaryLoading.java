package dictionaryLoading;

import java.sql.*;
import java.util.*;

public class DictionaryLoading {
    private static Connection connection;
    private static Map<String, Homonym> wordDictionary = null;
    private static Map<String, IdiomProperty[]> idiomDictionary = null;
    private static Lemma[] lemmaDictionary = null;

    public static boolean loadDictionary() {
        System.out.println("Dictionary are loading...");
        long startTime = System.currentTimeMillis();
        if (isLoadedDBDriver()) {
            if (isLoadedDictionary()) {
                long finishTime = System.currentTimeMillis();
                System.out.println("Dictionary are loaded!");
                System.out.println("Loading time: " + String.format("%,5d", (finishTime - startTime) / 1000) + " s");
                return true;
            }
        }
        return false;
    }

    private static boolean isLoadedDBDriver() {
        boolean sign = false;
        try {
            Class.forName("org.sqlite.JDBC");
            sign = true;
        } catch (ClassNotFoundException e) {
            System.out.println("DB driver wasn't loaded!");
        }
        return sign;
    }

    private static boolean isLoadedDictionary() {
        boolean sign = false;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
            wordDictionary = createWordDictionary();
            idiomDictionary = createIdiomDictionary();
            lemmaDictionary = createLemmaDictionary();
            addIdiomSignToWords();
            printIdiomWords();
            sign = true;
        } catch (SQLException e) {
            System.out.println("Dictionary wasn't loaded!");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Connection wasn't closed!");
            }
        }
        return sign;
    }

    private static void addIdiomSignToWords() {
        idiomDictionary.keySet().forEach(key -> {
            for (IdiomProperty idiomProperty : idiomDictionary.get(key)) {
                String idiomTail = idiomProperty.getIdiomTail();
                int idiomWordsNumber = 2;
                if (idiomTail != null) {
                    int idiomLength = idiomTail.length();
                    for (int i = 0; i < idiomLength; i++) {
                        if (idiomTail.charAt(i) == ' ') {
                            idiomWordsNumber++;
                        }
                    }
                    idiomWordsNumber++;
                }
                String firstWord = key.substring(0, key.indexOf(' '));
                Homonym homonym = wordDictionary.get(firstWord);
                if (homonym != null) {
                    int wordsNumber = homonym.getWordsNumber();
                    // !!! неправильно !!!
                    if (wordsNumber == 0 || wordsNumber > idiomWordsNumber) {
                        homonym.setWordsNumber(idiomWordsNumber);
                    }
                } else {
                    // TODO: Обработать слова, с которых начинается идиома, но в словаре таких слов нет! Как вариант, неопределенные слова проверять в словаре идиом!!!
                    // Или НЕТ! Автоматически добавлять такие слова при поиске количества слов в словарь слов!!!
                    // System.out.println(firstWord);
                }
            }
        });
    }

    private static void printIdiomWords() {
        for (String key : idiomDictionary.keySet()) {
            String firstWord = key.substring(0, key.indexOf(' '));
            Homonym homonym = wordDictionary.get(firstWord);
            if (homonym != null) {
                int wordsNumber = homonym.getWordsNumber();
                if (wordsNumber > 2) {
                    for (IdiomProperty idiomProperty : idiomDictionary.get(key)) {
                        String idiomTail = idiomProperty.getIdiomTail();
                        System.out.println(key + "   *** " + idiomTail + " **** " + "--- |" + firstWord + "|   " + wordsNumber);
                    }
                    System.out.println("------------------------------------------");
                }
            }
        }
    }


    // Метод создает и возвращает Map со словами из словаря (омонимы представлены одной записью), тегами и id леммы
    private static Map<String, Homonym> createWordDictionary() throws SQLException {
        Map<String, Homonym> wordMap = new HashMap<>(getWordCount());
        List<WordProperty> homonyms = new ArrayList<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select word,lemma_id,part_of_speech,tag from dictionary where word_type_id=1 order by word")) {

            rs.next();
            String word = rs.getString("word");
            String oldWord = word;
            homonyms.add(new WordProperty(rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            while (rs.next()) {
                word = rs.getString("word");
                if (!word.equals(oldWord)) {
                    wordMap.put(oldWord, new Homonym(homonyms.toArray(new WordProperty[homonyms.size()])));
                    homonyms.clear();
                    oldWord = word;
                }
                homonyms.add(new WordProperty(rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            }
            wordMap.put(word, new Homonym(homonyms.toArray(new WordProperty[homonyms.size()])));
        }
        return wordMap;
    }

    private static int getWordCount() throws SQLException {
        int wordCount;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select count() count from (select 1 from dictionary where word_type_id=1 group by word)")) {

            rs.next();
            wordCount = rs.getInt("count");
        }
        return wordCount;
    }

    // Метод создает и возвращает Map с идиомами из словаря (омонимы представлены одной записью), тегами и id леммы
    // Алгоритм работы: извлекаются из базы все записи, в которых есть пробелы - каждая идиома состоит из более одной словоформы, а значит, имеет пробел
    // Ключем Map определяется значение первых двух словоформ с пробелом по середине.
    // Это сделано для первичного отбора словоформ. На первом этапе отбираются последовательности слов, у которых первых две словоформы совпадают.
    // Далее ведется поиск идиомы путем перебора всех значений в Map, у которых ключ совпадает, и определением включает ли текст перебираемую идиому
    // с места окончания двух взятых слов. В случае, если определено соответствие идиомы с последовательностью слов в тексте, далее при добавлении тегов проверяются только
    // точно такие же (на случай если одна и та же идиома имеет разные теги или является другой частью речи). Идиомы упорядочены по-убыванию, поэтому в начале проверяются идиомы с наибольшим количеством слов
    // Так как более длинная идиома может включать в себя идиому короче.
    private static Map<String, IdiomProperty[]> createIdiomDictionary() throws SQLException {
        Map<String, IdiomProperty[]> idiomMap = new HashMap<>(getIdiomCount());
        List<IdiomProperty> homonyms = new ArrayList<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select lemma_id,part_of_speech,tag,case when instr(substr(word,instr(word,' ')+1),' ')=0 then replace(word,',','') else replace(substr(word,0,instr(word,' ')+instr(substr(word,instr(word,' ')+1),' ')),',','') end idiom_head,case when instr(substr(word,instr(word,' ')+1),' ')=0 then null else replace(substr(word,instr(word,' ')+instr(substr(word,instr(word,' ')+1),' ')+1,length(word)),',','') end idiom_tail from dictionary where word_type_id=2 order by idiom_head desc,word desc")) {

            rs.next();
            String idiomKey = rs.getString("idiom_head");
            String oldIdiomKey = idiomKey;
            homonyms.add(new IdiomProperty(rs.getString("idiom_tail"), rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            while (rs.next()) {
                idiomKey = rs.getString("idiom_head");
                if (!idiomKey.equals(oldIdiomKey)) {
                    idiomMap.put(oldIdiomKey, homonyms.toArray(new IdiomProperty[homonyms.size()]));
                    homonyms.clear();
                    oldIdiomKey = idiomKey;
                }
                homonyms.add(new IdiomProperty(rs.getString("idiom_tail"), rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            }
            idiomMap.put(idiomKey, homonyms.toArray(new IdiomProperty[homonyms.size()]));
        }
        return idiomMap;
    }

    private static int getIdiomCount() throws SQLException {
        int wordCount;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select count() count from (select 1 from dictionary where word_type_id=2 group by word)")) {

            rs.next();
            wordCount = rs.getInt("count");
        }
        return wordCount;
    }

    // Метод создает и возвращает массив лемм с тегами
    private static Lemma[] createLemmaDictionary() throws SQLException {
        Lemma[] lemmas = new Lemma[getLemmaCount()];

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select lemma_id,part_of_speech,word,tag from dictionary where id=base_id")) {

            while (rs.next()) {
                lemmas[rs.getInt("lemma_id") - 1] = new Lemma(rs.getString("word"), rs.getString("part_of_speech"), rs.getString("tag"));
            }
        }
        return lemmas;
    }

    private static int getLemmaCount() throws SQLException {
        int lemmaCount;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select max(id) count from dic_lemmas")) {

            rs.next();
            lemmaCount = rs.getInt("count");
        }
        return lemmaCount;
    }

    public static Map<String, Homonym> getWordDictionary() {
        return wordDictionary;
    }

    public static Map<String, IdiomProperty[]> getIdiomDictionary() {
        return idiomDictionary;
    }

    public static Lemma[] getLemmaDictionary() {
        return lemmaDictionary;
    }
}
