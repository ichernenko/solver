package dictionaryLoading;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryLoading {
    private static Map<String, WordProperty[]> wordDictionary = null;
    private static Map<String, IdiomProperty[]> idiomDictionary = null;
    private static Lemma[] lemmaDictionary = null;

    public static boolean loadDictionary() {
        System.out.println("Dictionary are loading...");
        long startTime = System.currentTimeMillis();
        if (isLoadedDBDriver()) {
            if (isLoadedDictionary()) {
                long finishTime = System.currentTimeMillis();
                System.out.println("Dictionary are loaded!");
                System.out.println("Loading time: " + String.format("%,5d",(finishTime - startTime) / 1000) + " s");
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

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db")) {
            wordDictionary = createWordDictionary(con);
            idiomDictionary = createIdiomDictionary(con);
            lemmaDictionary = createLemmaDictionary(con);
            sign = true;
        } catch (SQLException e) {
            System.out.println("Dictionary wasn't loaded!");
        }
        return sign;
    }

    // Метод создает и возвращает Map со словами из словаря (омонимы представлены одной записью), тегами и id леммы
    private static Map<String, WordProperty[]> createWordDictionary(Connection con) throws SQLException{
        Map<String, WordProperty[]> wordMap = new HashMap<>(getWordCount(con));
        List<WordProperty> homonyms = new ArrayList<>();

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select word,lemma_id,part_of_speech,tag from dictionary where word_type_id=1 order by word")) {

            rs.next();
            String word = rs.getString("word");
            String oldWord = word;
            homonyms.add(new WordProperty(rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            while (rs.next()) {
                word = rs.getString("word");
                if (!word.equals(oldWord)) {
                    wordMap.put(oldWord, homonyms.toArray(new WordProperty[homonyms.size()]));
                    homonyms.clear();
                    oldWord = word;
                }
                homonyms.add(new WordProperty(rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            }
            wordMap.put(word, homonyms.toArray(new WordProperty[homonyms.size()]));
        }
        return wordMap;
    }

    private static int getWordCount(Connection con) throws SQLException {
        int wordCount;
        try (Statement st = con.createStatement();
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
    private static Map<String, IdiomProperty[]> createIdiomDictionary(Connection con) throws SQLException{
        Map<String, IdiomProperty[]> idiomMap = new HashMap<>(getIdiomCount(con));
        List<IdiomProperty> homonyms = new ArrayList<>();

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select lemma_id,part_of_speech,word idiom,tag,case when instr(substr(word,instr(word,' ')+1),' ')=0 then word else substr(word,0,instr(word,' ')+instr(substr(word,instr(word,' ')+1),' ')) end idiom_key from dictionary where word_type_id=2 order by idiom_key desc,word desc")) {

            rs.next();
            String idiomKey = rs.getString("idiom_key");
            String oldIdiomKey = idiomKey;
            homonyms.add(new IdiomProperty(rs.getString("idiom"), rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            while (rs.next()) {
                idiomKey = rs.getString("idiom_key");
                if (!idiomKey.equals(oldIdiomKey)) {
                    idiomMap.put(oldIdiomKey, homonyms.toArray(new IdiomProperty[homonyms.size()]));
                    homonyms.clear();
                    oldIdiomKey = idiomKey;
                }
                homonyms.add(new IdiomProperty(rs.getString("idiom"), rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            }
            idiomMap.put(idiomKey, homonyms.toArray(new IdiomProperty[homonyms.size()]));
        }
        return idiomMap;
    }

    private static int getIdiomCount(Connection con) throws SQLException {
        int wordCount;
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select count() count from (select 1 from dictionary where word_type_id=2 group by word)")) {

            rs.next();
            wordCount = rs.getInt("count");
        }
        return wordCount;
    }

    // Метод создает и возвращает массив лемм с тегами
    private static Lemma[] createLemmaDictionary(Connection con) throws SQLException{
        Lemma[] lemmas = new Lemma[getLemmaCount(con)];

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select lemma_id,part_of_speech,word,tag from dictionary where id=base_id")) {

            while (rs.next()) {
                lemmas[rs.getInt("lemma_id") - 1] = new Lemma(rs.getString("word"), rs.getString("part_of_speech"), rs.getString("tag"));
            }
        }
        return lemmas;
    }

    private static int getLemmaCount(Connection con) throws SQLException {
        int lemmaCount;
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select max(id) count from dic_lemmas")) {

            rs.next();
            lemmaCount = rs.getInt("count");
        }
        return lemmaCount;
    }

    public static Map<String, WordProperty[]> getWordDictionary() {
        return wordDictionary;
    }
    public static Map<String, IdiomProperty[]> getIdiomDictionary() {
        return idiomDictionary;
    }
    public static Lemma[] getLemmaDictionary() {
        return lemmaDictionary;
    }
}
