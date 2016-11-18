package utils.crawling.neolove;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class NeoloveGroupNameShrinking {
    private static final String selectSql = "select id,description from dic_names_groups order by description";
    private static final String deleteSql = "delete from dic_names_groups where id=?";
    private static final String updateSql = "update dic_names_ru set group_id=? where group_id=?";

    private void loadDBDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void update() {
        List<Integer> idList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             PreparedStatement selectPS = con.prepareStatement(selectSql);
             PreparedStatement deleteNamesGroupsPS = con.prepareStatement(deleteSql);
             PreparedStatement updateIdsPS = con.prepareStatement(updateSql);
             ResultSet rs = selectPS.executeQuery()) {
            con.setAutoCommit(false);

            rs.next();
            int oldId = rs.getInt("id");
            String oldDescription = rs.getString("description");
            int count = 1;
            int commonCount = 1;
            int groupCount = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");

                if(description.equals(oldDescription)) {
                    count++;
                    commonCount++;
                    idList.add(id);
                } else {
                    if (count > 1) {
                        groupCount ++;
                        System.out.println(oldId + "   " + oldDescription + "    "  + idList + "   " + count);
                        deleteGroups(deleteNamesGroupsPS, idList);
                        updateIds(updateIdsPS, idList, oldId);
                        idList.clear();
                        count = 1;
                    }
                    oldId = id;
                    oldDescription = description;
                }
            }
            if (count > 1) {
                groupCount ++;
                System.out.println(oldId + "   " + oldDescription + "    "  + idList + "   " + count);
                deleteGroups(deleteNamesGroupsPS, idList);
                updateIds(updateIdsPS, idList, oldId);
                idList.clear();
            }

            System.out.println("Common Count: " + commonCount);
            System.out.println("Group Count: " + groupCount);

            deleteNamesGroupsPS.executeBatch();
            updateIdsPS.executeBatch();

            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteGroups(PreparedStatement ps, List<Integer> idList) throws SQLException {
        for (int groupId: idList) {
            ps.setInt(1, groupId);
            ps.addBatch();
        }
    }

    private void updateIds(PreparedStatement ps, List<Integer> idList, int newGroupId) throws SQLException {
        for (int oldGroupId: idList) {
            ps.setInt(1, newGroupId);
            ps.setInt(2, oldGroupId);
            ps.addBatch();
        }
    }


    public static void main(String[] args) throws IOException {
        NeoloveGroupNameShrinking neoloveGroupNameShrinking = new NeoloveGroupNameShrinking();
        neoloveGroupNameShrinking.loadDBDriver();
        neoloveGroupNameShrinking.update();
    }
}
