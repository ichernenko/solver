import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class PhraseHandler {
    private String phrase = null;
    private List<Lexeme> lexemeList = null;

    PhraseHandler(String phrase) {
        this.phrase = phrase;
        //getAllVerbs();
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
             ResultSet rs = st.executeQuery("SELECT * FROM verbs")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("verb");
                System.out.println("ID = " + id);
                System.out.println("name = " + name);
                System.out.println();
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
                    lexeme.setEndPosition(i - 1);
                    isLexeme = false;
                }
            }
        }

        if (isLexeme) {
            lexeme.setEndPosition(phrase.length() - 1);
        }
    }

    void printAllLexemes() {
        System.out.println("List of lexemes");
        for(Lexeme lexeme : lexemeList) {
            System.out.println(lexeme.getStartPosition() + "," + lexeme.getEndPosition());
        }
    }
}

class Lexeme {
    private int startPosition;
    private int endPosition;

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
}