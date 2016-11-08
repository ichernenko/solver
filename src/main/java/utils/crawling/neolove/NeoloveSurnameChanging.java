package utils.crawling.neolove;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class NeoloveSurnameChanging {
//    private static final String cause = "substr(surname,length(surname)-2,length(surname))=\"ЁВЫ\"";
    private static final String cause = " where upper(surname) != surname";
    private static final String selectSql = "select id,surname,sign from dic_surnames_ru";
    private static final String updateSql = "update dic_surnames_ru set surname=? where id=?";

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
             PreparedStatement updatePS = con.prepareStatement(updateSql);
             ResultSet rs = selectPS.executeQuery()) {
            con.setAutoCommit(false);
            int i = 0;
            while(rs.next()) {
                int id = rs.getInt("id");
                String surname = rs.getString("surname");
                String sign = rs.getString("sign");

                int flag = 0;
                for(int k = 0; k < surname.length(); k++) {
                    char ch = surname.charAt(k);
//                    if(!((ch >= 'А' && ch <= 'я') || ch=='Ё' || ch=='ё' || ch=='-')) {
                        if(((ch >= 'А' && ch <= 'Я') || ch=='Ё')) {
//                        if (ch=='є') {
                            if (!(k>0 && surname.charAt(k - 1) == '-')) {
                                flag++;
//                            }
                            //break;
                        }
                    }
                }

                if (flag>1) {
                    i ++;
                    System.out.print(id + " " + surname);
//
                   surname = surname.substring(0,1).toUpperCase() + surname.substring(1).toLowerCase();
////                surname = surname.replace('Ё','е');
//
                    System.out.print("   " + surname);
                    System.out.println();
//
//                updateRow(updatePS, id, surname);
                }
            }
//            updatePS.executeBatch();
//            con.commit();

            System.out.println("Всего записей:" + i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateRow(PreparedStatement ps, int id, String surname) throws SQLException {
        ps.setString(1, surname);
        ps.setInt(2, id);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        NeoloveSurnameChanging neoloveSurnameChanging = new NeoloveSurnameChanging();
        neoloveSurnameChanging.loadDBDriver();
        neoloveSurnameChanging.update();
    }
}
