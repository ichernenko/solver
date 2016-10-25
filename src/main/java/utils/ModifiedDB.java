package utils;


import dictionaryLoading.WordProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModifiedDB {

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
            changeTable(con);
            sign = true;
        } catch (SQLException e) {
            System.out.println("Dictionary wasn't loaded!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }


    private static void changeTable(Connection con) throws Exception {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select wd.word wd, id.word id from (select * from word_dictionary group by word) wd, (select * from idiom_dictionary group by word) id where id.word like wd.word||\" %\"")) {
            int i = 0;
            while(rs.next()) {
                i ++;
                String wd = rs.getString("wd");
                String id = rs.getString("id");
                System.out.println(i + "--- " + wd + "   " + id);
            }
        }
    }

    public static void main(String[] args) {
        loadDictionary();
    }
}
