package dictionaryLoading;

import java.sql.*;
import java.util.*;

public class DictionaryLoading {
    private static Connection connection;
    private static Map<String, Homonym> wordDictionary;
    private static Map<String, IdiomProperty[]> idiomDictionary;
    private static Lemma[] lemmaDictionary;
    private static Map<String, NameProperty> nameRuDictionary;

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
            createWordDictionary();
            createIdiomDictionary();
            createLemmaDictionary();
            createNameRuDictionary();
            markIdiomWords();
//            printIdiomWords();
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

    // Метод создает Map со словами из словаря (омонимы представлены одной записью), тегами и id леммы
    private static void createWordDictionary() throws SQLException {
        wordDictionary = new HashMap<>(getWordCount());
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
                    wordDictionary.put(oldWord, new Homonym(homonyms.toArray(new WordProperty[homonyms.size()])));
                    homonyms.clear();
                    oldWord = word;
                }
                homonyms.add(new WordProperty(rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            }
            wordDictionary.put(word, new Homonym(homonyms.toArray(new WordProperty[homonyms.size()])));
        }
    }

    // Метод возвращает количество слов в словаре dictionary
    private static int getWordCount() throws SQLException {
        int wordCount;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select count() count from (select 1 from dictionary where word_type_id=1 group by word)")) {

            rs.next();
            wordCount = rs.getInt("count");
        }
        return wordCount;
    }

    // Метод создает Map с идиомами из словаря (омонимы представлены одной записью), тегами и id леммы
    // Алгоритм работы: извлекаются из базы все записи, в которых есть пробелы - каждая идиома состоит из более одной словоформы, а значит, имеет пробел
    // Ключем Map определяется значение первых двух словоформ с пробелом по середине.
    // Это сделано для первичного отбора словоформ. На первом этапе отбираются последовательности слов, у которых первых две словоформы совпадают.
    // Далее ведется поиск идиомы путем перебора всех значений в Map, у которых ключ совпадает, и определением включает ли текст перебираемую идиому
    // с места окончания двух взятых слов. В случае, если определено соответствие идиомы с последовательностью слов в тексте, далее при добавлении тегов проверяются только
    // точно такие же (на случай если одна и та же идиома имеет разные теги или является другой частью речи). Идиомы упорядочены по-убыванию, поэтому в начале проверяются идиомы с наибольшим количеством слов
    // Так как более длинная идиома может включать в себя идиому короче.
    private static void createIdiomDictionary() throws SQLException {
        idiomDictionary = new HashMap<>(getIdiomCount());
        List<IdiomProperty> homonyms = new ArrayList<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select lemma_id,part_of_speech,tag,case when instr(substr(word,instr(word,' ')+1),' ')=0 then replace(word,',','') else replace(substr(word,0,instr(word,' ')+instr(substr(word,instr(word,' ')+1),' ')),',','') end idiom_head,case when instr(substr(word,instr(word,' ')+1),' ')=0 then null else replace(substr(word,instr(word,' ')+instr(substr(word,instr(word,' ')+1),' ')+1,length(word)),',','') end idiom_tail from dictionary where word_type_id=2 order by idiom_head desc,word desc")) {

            rs.next();
            String idiomKey = rs.getString("idiom_head");
            String oldIdiomKey = idiomKey;
            String idiomTail = rs.getString("idiom_tail");
            homonyms.add(new IdiomProperty(idiomTail, getIdiomTailLexemesNumber(idiomTail), rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            while (rs.next()) {
                idiomKey = rs.getString("idiom_head");
                if (!idiomKey.equals(oldIdiomKey)) {
                    idiomDictionary.put(oldIdiomKey, homonyms.toArray(new IdiomProperty[homonyms.size()]));
                    homonyms.clear();
                    oldIdiomKey = idiomKey;
                }
                idiomTail = rs.getString("idiom_tail");
                homonyms.add(new IdiomProperty(idiomTail, getIdiomTailLexemesNumber(idiomTail), rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            }
            idiomDictionary.put(idiomKey, homonyms.toArray(new IdiomProperty[homonyms.size()]));
        }
    }

    // Метод возвращает количество идиом в таблице dictionary
    private static int getIdiomCount() throws SQLException {
        int wordCount;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select count() count from (select 1 from dictionary where word_type_id=2 group by word)")) {

            rs.next();
            wordCount = rs.getInt("count");
        }
        return wordCount;
    }

    // Метод подсчитывает количество лексем в хвосте идиомы
    private static int getIdiomTailLexemesNumber(String idiomTail) {
        if (idiomTail == null) {
            return 0;
        }

        int idiomTailLexemesNumber = 1;
        int idiomTailLength = idiomTail.length();
        for (int i = 0; i < idiomTailLength; i++) {
            if (idiomTail.charAt(i) == ' ') {
                idiomTailLexemesNumber++;
            }
        }
        return idiomTailLexemesNumber;
    }


    // Метод перебирает все идиомы и ищет первое слово идиомы в словаре слов
    // Если находит -> в словаре ставит признак, обозначающий, что с этого слова может начинаться идиома.
    // Признак (wordsNumber) показывает наименьшее количество слов для ВСЕХ идиом, начинающихся с этого слова.
    // Если не находит -> создает новое слово в словаре слов, начинающееся с первого слова идиомы.
    private static void markIdiomWords() {
        idiomDictionary.keySet().forEach(key -> {
            for (IdiomProperty idiomProperty : idiomDictionary.get(key)) {
                String idiomTail = idiomProperty.getIdiomTail();
                int idiomWordsNumber = 2;
                if (idiomTail != null) {
                    int idiomTailLength = idiomTail.length();
                    for (int i = 0; i < idiomTailLength; i++) {
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
                    if (wordsNumber == 0 || wordsNumber > idiomWordsNumber) {
                        homonym.setWordsNumber(idiomWordsNumber);
                    }
                } else {
                    homonym = new Homonym(new WordProperty[]{});
                    homonym.setWordsNumber(idiomWordsNumber);
                    wordDictionary.put(firstWord, homonym);
                }
            }
        });
    }


    // Метод создает массив лемм с тегами
    private static void createLemmaDictionary() throws SQLException {
        lemmaDictionary = new Lemma[getLemmaCount()];

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select lemma_id,part_of_speech,word,tag from dictionary where id=base_id")) {

            while (rs.next()) {
                lemmaDictionary[rs.getInt("lemma_id") - 1] = new Lemma(rs.getString("word"), rs.getString("part_of_speech"), rs.getString("tag"));
            }
        }
    }

    // Метод возвращает количество строк в таблице dic_lemmas
    private static int getLemmaCount() throws SQLException {
        int lemmaCount;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select max(id) count from dic_lemmas")) {

            rs.next();
            lemmaCount = rs.getInt("count");
        }
        return lemmaCount;
    }


    // Метод создает Map с русскими именами из словаря и полами (M - мужской; F - женский; B - оба)
    private static void createNameRuDictionary() throws SQLException {
        nameRuDictionary = new HashMap<>(getNameRuCount());

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select name,sex from dic_names_ru")) {

            while (rs.next()) {
                nameRuDictionary.put(rs.getString("name").toLowerCase(), new NameProperty(rs.getString("sex").charAt(0)));
            }
        }
    }

    // Метод возвращает количество строк в таблице dic_names_ru
    private static int getNameRuCount() throws SQLException {
        int lemmaCount;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select count(id) count from dic_names_ru")) {

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
    public static Map<String, NameProperty> getNameRuDictionary() {
        return nameRuDictionary;
    }
}
