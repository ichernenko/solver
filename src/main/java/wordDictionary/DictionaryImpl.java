package wordDictionary;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryImpl implements Dictionary{
    private static Dictionary dictionary = new DictionaryImpl();
    private Map<String, WordProperty[]> wordMap = null;
    private Lemma[] lemmas = null;

    private DictionaryImpl() {
        System.out.println("Dictionary are loading...");
        long startTime = System.currentTimeMillis();
        if (loadDBDriver()) {
            if (loadDictionary()) {
                long finishTime = System.currentTimeMillis();
                System.out.println("Dictionary are loaded!");
                System.out.println("Loading time: " + String.format("%,5d",(finishTime - startTime) / 1000) + " s");
                System.out.println("wordMap size: " + wordMap.size());

            }
        }
    }

    private boolean loadDBDriver() {
        boolean sign = false;
        try {
            Class.forName("org.sqlite.JDBC");
            sign = true;
        } catch (ClassNotFoundException e) {
            System.out.println("DB driver wasn't loaded!");
        }
        return sign;
    }

    private boolean loadDictionary() {
        boolean sign = false;

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db")) {
            wordMap = createWordMap(con);
            lemmas = createLemmas(con);
            sign = true;
        } catch (SQLException e) {
            System.out.println("Dictionary wasn't loaded!");
        }
        return sign;
    }

    // Метод создает и возвращает Map со словами из словаря (омонимы представлены одной записью), тегами и id леммы
    private Map<String, WordProperty[]> createWordMap(Connection con) throws SQLException{
        Map<String, WordProperty[]> wordMap = new HashMap<>(getWordCount(con));
        List<WordProperty> homonyms = new ArrayList<>();

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select lemma_id,part_of_speech,word,tag from dictionary order by word")) {

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

    private int getWordCount(Connection con) throws SQLException {
        int wordCount;
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select count() count from (select 1 from dictionary group by word)")) {

            rs.next();
            wordCount = rs.getInt("count");
        }
        return wordCount;
    }

    // Метод создает и возвращает массив лемм с тегами
    private Lemma[] createLemmas(Connection con) throws SQLException{
        Lemma[] lemmas = new Lemma[getLemmaCount(con)];

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select lemma_id,part_of_speech,word,tag from dictionary where id=base_id")) {

            while (rs.next()) {
                lemmas[rs.getInt("lemma_id") - 1] = new Lemma(rs.getString("word"), rs.getString("part_of_speech"), rs.getString("tag"));
            }
        }
        return lemmas;
    }


    private int getLemmaCount(Connection con) throws SQLException {
        int lemmaCount;
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select max(id) count from dic_lemmas")) {

            rs.next();
            lemmaCount = rs.getInt("count");
        }
        return lemmaCount;
    }

    public static Dictionary getInstance() {
        return dictionary;
    }
    @Override
    public Map<String, WordProperty[]> getWordMap() {
        return wordMap;
    }
    @Override
    public Lemma[] getLemmas() {
        return lemmas;
    }
}
