import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class PhraseHandler {
    private String phrase = null;

    PhraseHandler(String phrase) {
        this.phrase = phrase;
        getAllVerbs();
    }

    private void getAllVerbs() {
        if(loadDBDriver()) {
            if(loadAllVerbs()) {
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
}
