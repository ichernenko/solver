import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PhraseHandler {
    private String phrase = null;
    private Map<String, WordProperty> wordPropertyMap = null;
    private List<Lexeme> lexemeList = null;

    PhraseHandler(String phrase) {
        this.phrase = phrase;
        getAllVerbs();
        parseSentence(phrase);
    }

    private void getAllVerbs() {
        if (loadDBDriver()) {
            if (loadAllVerbs()) {
                System.out.println("All verbs are loaded!");
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

    private boolean loadAllVerbs() {
        boolean sign = false;
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select id,lexeme from bas_main_dictionary where type = 'гл'")) {

            wordPropertyMap = new HashMap<>();

            while (rs.next()) {
                wordPropertyMap.put(rs.getString("lexeme"), new WordProperty(rs.getInt("id")));
            }
            sign = true;
        } catch (SQLException e) {
            System.out.println("Verbs weren't loaded!");
        }

        return sign;
    }

    private void parseSentence(String phrase) {
        lexemeList = new ArrayList<>();
        Lexeme lexeme = null;
        boolean isLexeme = false;

        for (int i = 0; i < phrase.length(); i++) {
            if ((phrase.charAt(i) >= 'А' && phrase.charAt(i) <= 'п') || (phrase.charAt(i) >= 'р' && phrase.charAt(i) <= 'ё')) {
                if (!isLexeme) {
                    lexeme = new Lexeme(i);
                    lexemeList.add(lexeme);
                    isLexeme = true;
                }
            } else {
                if (isLexeme) {
                    lexeme.setEndPosition(i);
                    isLexeme = false;
                }
            }
        }

        if (isLexeme) {
            lexeme.setEndPosition(phrase.length());
        }
    }

    void printAllLexemes() {
        System.out.println("List of lexemes");
        lexemeList.forEach(k -> System.out.println(k.getStartPosition() + ", " + k.getEndPosition()));
    }

    void printAllWordProperty() {
        System.out.println("List of words properties");
        wordPropertyMap.forEach((k,v) -> System.out.println(k + ", " + v.getId()));
    }

    void findLexemeInDictionary() {
        lexemeList.forEach(k -> k.setInDictionary(wordPropertyMap.containsKey(phrase.substring(k.getStartPosition(), k.getEndPosition()))));
    }

    void printAllFoundLexemes() {
        lexemeList.forEach(k -> {if (k.isInDictionary())  System.out.println(phrase.substring(k.getStartPosition(), k.getEndPosition()));});
    }

}

class Lexeme {
    private int startPosition;
    private int endPosition;
    private boolean isInDictionary = false;

    Lexeme(int startPosition) {
        this.startPosition = startPosition;
    }

    int getStartPosition() {
        return startPosition;
    }

    int getEndPosition() {
        return endPosition;
    }

    void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }

    boolean isInDictionary() {
        return isInDictionary;
    }

    void setInDictionary(boolean inDictionary) {
        isInDictionary = inDictionary;
    }
}

class WordProperty {
    private int id;

    WordProperty(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }
}