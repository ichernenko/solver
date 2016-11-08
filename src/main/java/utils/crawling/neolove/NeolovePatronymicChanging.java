package utils.crawling.neolove;

import java.io.IOException;
import java.sql.*;

class NeolovePatronymicChanging {
    private static final String selectSql = "select patronymic,tag from dic_patronymics_ru order by patronymic";
    private static final String insertSql = "insert into dic_patronymics(patronymic, tag) values (?, ?)";

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
                String patronymic = rs.getString("patronymic");
                String tag = rs.getString("tag");

                     insertRow(updatePS, patronymic, tag);
            }
            updatePS.executeBatch();
            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void insertRow(PreparedStatement ps, String patronymic, String tag) throws SQLException {
        ps.setString(1, patronymic);
        ps.setString(2, tag);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        NeolovePatronymicChanging neolovePatronymicChanging = new NeolovePatronymicChanging();
        neolovePatronymicChanging.loadDBDriver();
        neolovePatronymicChanging.update();
    }
}
