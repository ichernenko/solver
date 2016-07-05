import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PhraseHandler {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    private String phrase = null;
    static String fileName = "src\\main\\resources\\mainDictionary.dic";


    PhraseHandler(String phrase) {
        this.phrase = phrase;
        getAllVerbs();
    }

    public void printFoundVerb() {
        try {
            Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)
                    .filter(a -> a.equals(phrase))
                    .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("TTTTT");
        }
    }

    private void getAllVerbs() {
        openDB();
        readDB();
        closeDB();

    }

    private static void openDB() {
        conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("База Подключена!");
    }

    public static void readDB() {
        try {
            statmt = conn.createStatement();
            resSet = statmt.executeQuery("SELECT * FROM verbs");

            while (resSet.next()) {
                int id = resSet.getInt("id");
                String name = resSet.getString("verb");
                System.out.println("ID = " + id);
                System.out.println("name = " + name);
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Таблица выведена");
    }

    public static void closeDB() {
        try {
            resSet.close();
            statmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("DB is closed");
    }
}
