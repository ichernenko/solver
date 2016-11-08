package utils.crawling.neolove;

import java.io.IOException;
import java.sql.*;

class NeoloveSurnameGrouping {
    private static final String selectSql = "select surname from dic_surnames_ru group by surname order by surname";
    private static final String insertSql = "insert into dic_surnames(surname) values (?)";

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
             PreparedStatement updatePS = con.prepareStatement(insertSql);
             ResultSet rs = selectPS.executeQuery()) {
            con.setAutoCommit(false);
            while(rs.next()) {
                String surname = rs.getString("surname");

                insertRow(updatePS, surname);
            }
            updatePS.executeBatch();
            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void insertRow(PreparedStatement ps, String surname) throws SQLException {
        ps.setString(1, surname);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        NeoloveSurnameGrouping neoloveSurnameGrouping = new NeoloveSurnameGrouping();
        neoloveSurnameGrouping.loadDBDriver();
        neoloveSurnameGrouping.update();
    }
}
