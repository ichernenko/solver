package utils.crawling.neolove;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class NeoloveDB{

    public static boolean loadDictionary() {
        System.out.println("Processing...");
        long startTime = System.currentTimeMillis();
        if (isLoadedDBDriver()) {
            long finishTime = System.currentTimeMillis();
            System.out.println("Processing is finished!");
            System.out.println("Processing time: " + String.format("%,5d", (finishTime - startTime) / 1000) + " s");
            return true;
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

    private static void parseSite() {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select id,surname from dic_surnames_ru");) {

        } catch (SQLException e) {
            System.out.println("Processing wasn't finished!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        if(loadDictionary()) {
            parseSite();
        }
    }
}