package utils.crawling.neolove;

import java.io.IOException;
import java.sql.*;

class NeoloveSurnameDuplicateDeleting {
    private static final String selectSql = "select surname,tag from dic_surnames group by surname,tag order by surname";
    private static final String insertSql = "insert into dic_surnames1(surname,tag) values (?,?)";

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
                String tag = rs.getString("tag");

                insertRow(updatePS, surname, tag);
            }
            updatePS.executeBatch();
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
        NeoloveSurnameDuplicateDeleting neoloveSurnameDuplicateDeleting = new NeoloveSurnameDuplicateDeleting();
        neoloveSurnameDuplicateDeleting.loadDBDriver();
        neoloveSurnameDuplicateDeleting.update();
    }
}
