package utils.crawling.neolove;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


class NeoloveSurnameMaleSign {
    private static final String selectSql = "select surname from dic_surnames";
    private static final String insertSql = "insert into dic_surnames(surname,tag) values (?,?)";

    private void loadDBDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             PreparedStatement selectPS = con.prepareStatement(selectSql);
             PreparedStatement insertPS = con.prepareStatement(insertSql);
             ResultSet rs = selectPS.executeQuery()) {
            con.setAutoCommit(false);

            int i = 0;
            while(rs.next()) {
                String surname = rs.getString("surname");

                if ((surname.endsWith("ов") || surname.endsWith("ев") || surname.endsWith("ив") || surname.endsWith("ин") || surname.endsWith("ын")) && !surname.endsWith("Сов") && !surname.endsWith("Зив")) {
                    System.out.println(i ++ + ". ов/ев/ив/ин/ын : " + surname + "   " + surname + 'а');
                    insertRow(insertPS, surname + 'а', "F");
                } else {
                    if (surname.endsWith("ий") || surname.endsWith("ый") || surname.endsWith("ой")) {
                        System.out.println(i ++ + ". ий/ый/ой : " + surname + "   " + surname.substring(0,surname.length()-2) + "ая");
                        insertRow(insertPS, surname.substring(0,surname.length()-2) + "ая", "F");
                   }
                }

            }
            insertPS.executeBatch();
            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void insertRow(PreparedStatement ps, String surname, String tag) throws SQLException {
        ps.setString(1, surname);
        ps.setString(2, tag);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        NeoloveSurnameMaleSign neoloveSurnameMaleSign = new NeoloveSurnameMaleSign();
        neoloveSurnameMaleSign.loadDBDriver();
        neoloveSurnameMaleSign.update();
    }
}