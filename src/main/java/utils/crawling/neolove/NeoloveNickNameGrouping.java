package utils.crawling.neolove;

import java.io.IOException;
import java.sql.*;

class NeoloveNickNameGrouping {
    private static final String selectSql = "select id,nationality,name,tag,derivative from dic_names_ru where derivative is not null order by name";
    private static final String insertGroupSql = "insert into dic_names_groups(id,description) values (?,?)";
    private static final String updateSql = "update dic_names_ru set group_id=? where id=?";
    private static final String insertSql = "insert into dic_names_ru(group_id,nationality,name,tag) values (?,?,?,?)";

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
             PreparedStatement insertPS = con.prepareStatement(insertSql);
             PreparedStatement insertGroupPS = con.prepareStatement(insertGroupSql);
             ResultSet rs = selectPS.executeQuery()) {
            con.setAutoCommit(false);

            int groupId = 314;
            while (rs.next()) {
                groupId++;
                int id = rs.getInt("id");
                String nationality = rs.getString("nationality");
                String name = rs.getString("name");
                String derivativeString = rs.getString("derivative");
                String tag = rs.getString("tag");

                String[] derivatives = derivativeString.split(",");

                insertGroupRow(insertGroupPS, groupId, name + ", " + derivativeString);
                updateRow(updatePS, groupId, id);

                for (int i = 0; i < derivatives.length; i++) {
                    String derivative = derivatives[i].trim();
                    insertRow(insertPS, groupId, nationality, derivative, tag + ", N");
                }
            }

            insertGroupPS.executeBatch();
            updatePS.executeBatch();
            insertPS.executeBatch();

            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertGroupRow(PreparedStatement ps, int id, String description) throws SQLException {
        ps.setInt(1, id);
        ps.setString(2, description);
        ps.addBatch();
    }

    private void insertRow(PreparedStatement ps, int groupId, String nationality, String name, String tag) throws SQLException {
        ps.setInt(1, groupId);
        ps.setString(2, nationality);
        ps.setString(3, name);
        ps.setString(4, tag);
        ps.addBatch();
    }

    private void updateRow(PreparedStatement ps, int groupId, int id) throws SQLException {
        ps.setInt(1, groupId);
        ps.setInt(2, id);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        NeoloveNickNameGrouping neoloveNickNameGrouping = new NeoloveNickNameGrouping();
        neoloveNickNameGrouping.loadDBDriver();
        neoloveNickNameGrouping.update();
    }
}
