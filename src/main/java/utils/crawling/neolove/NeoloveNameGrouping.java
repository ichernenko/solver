package utils.crawling.neolove;

import java.io.IOException;
import java.sql.*;

class NeoloveNameGrouping {
    private static final String selectSql = "select id,nationality,name,tag from dic_names_ru where instr(name,\",\")>0 order by name";
    private static final String updateSql = "update dic_names_ru set group_id=?,name=? where id=?";
    private static final String insertSql = "insert into dic_names_ru(group_id,nationality,name,tag) values (?,?,?,?)";
    private static final String insertGroupSql = "insert into dic_names_groups(id,description) values (?,?)";

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

            int groupId = 0;
            while (rs.next()) {
                groupId++;
                int id = rs.getInt("id");
                String nationality = rs.getString("nationality");
                String nameString = rs.getString("name");
                String tag = rs.getString("tag");

                String[] names = nameString.split(",");

                insertGroupRow(insertGroupPS, groupId, nameString);
                updateRow(updatePS, groupId, names[0].trim(), id);

                for (int i = 1; i < names.length; i++) {
                    String name = names[i].trim();
                    insertRow(insertPS, groupId, nationality, name, tag);
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

    private void updateRow(PreparedStatement ps, int groupId, String name, int id) throws SQLException {
        ps.setInt(1, groupId);
        ps.setString(2, name);
        ps.setInt(3, id);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        NeoloveNameGrouping neoloveNameGrouping = new NeoloveNameGrouping();
        neoloveNameGrouping.loadDBDriver();
        neoloveNameGrouping.update();
    }
}
