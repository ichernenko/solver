package utils.crawling.neolove;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class NeoloveNameDifferentNationality {
    private static final String selectSql = "select id,group_id,nationality,name,tag from dic_names_ru order by name,tag,nationality";
    private static final String selectNationalitiesSql = "select id,origin from dic_name_origins order by origin";
    private static final String insertSql = "insert into dic_names(id,name,tag) values (?,?,?)";
    private static final String insertNationalitySql = "insert into dic_names_nationalities(name_id,nationality_id) values (?,?)";
    private static final String insertGroupIdSql = "insert into dic_names_grs(name_id,group_id) values (?,?)";

    private void loadDBDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void update() {
        Set<String> nationalitySet = new HashSet<>();
        Set<Integer> groupIdSet = new HashSet<>();
        Map<String, Integer> nationalitiesMap = new HashMap<>();

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             PreparedStatement selectPS = con.prepareStatement(selectSql);
             PreparedStatement selectNationalitiesPS = con.prepareStatement(selectNationalitiesSql);
             PreparedStatement insertPS = con.prepareStatement(insertSql);
             PreparedStatement insertNationalityPS = con.prepareStatement(insertNationalitySql);
             PreparedStatement insertGroupIdPS = con.prepareStatement(insertGroupIdSql);
             ResultSet rs = selectPS.executeQuery();
             ResultSet rsNationality = selectNationalitiesPS.executeQuery()) {

            con.setAutoCommit(false);

            while(rsNationality.next()) {
                nationalitiesMap.put(rsNationality.getString("origin"), rsNationality.getInt("id"));
            }

            rs.next();
            String name = rs.getString("name");
            String oldName = name;
            String tag = rs.getString("tag");
            String oldTag = tag;
            String nationality = rs.getString("nationality");
            nationalitySet.add(nationality);
            int groupId = rs.getInt("group_id");
            groupIdSet.add(groupId);
            int count = 1;
            int id = 0;
            int groupCount = 0;
            while (rs.next()) {
                name = rs.getString("name");
                nationality = rs.getString("nationality");
                tag = rs.getString("tag");
                groupId = rs.getInt("group_id");

                if (name.equals(oldName) && tag.equals(oldTag)) {
                    nationalitySet.add(nationality);
                    groupIdSet.add(groupId);
                    count++;
                } else {
                    id++;
                    groupCount++;
                    insertName(insertPS, id, oldName, oldTag);
                    insertNationalities(insertNationalityPS, id, nationalitySet, nationalitiesMap);
                    insertGroups(insertGroupIdPS, id, groupIdSet);
                    System.out.println(oldName + "    " + nationalitySet + "    " + oldTag + "    " + groupIdSet + "   " + count);
                    count = 1;
                    nationalitySet.clear();
                    groupIdSet.clear();
                    nationalitySet.add(nationality);
                    groupIdSet.add(groupId);
                    oldName = name;
                    oldTag = tag;
                }
            }

            groupCount++;
            id++;
            insertName(insertPS, id, name, tag);
            System.out.println(oldName + "    " + nationalitySet + "    " + oldTag + "    " + groupIdSet + "   " + count);
            nationalitySet.clear();
            groupIdSet.clear();

            System.out.println();
            System.out.println("Common Count: " + id);
            System.out.println("Group Count: " + groupCount);

            insertPS.executeBatch();
            insertNationalityPS.executeBatch();
            insertGroupIdPS.executeBatch();

            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertName(PreparedStatement ps, int id, String name, String tag) throws SQLException {
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, tag);
        ps.addBatch();
    }

    private void insertNationalities(PreparedStatement ps, int id, Set<String> nationalitySet, Map<String, Integer> nationalitiesMap) {
        nationalitySet.forEach(nationality -> {
            try {
                ps.setInt(1, id);
                ps.setInt(2, nationalitiesMap.get(nationality));
                ps.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void insertGroups(PreparedStatement ps, int id, Set<Integer> groupIdSet) {
        groupIdSet.forEach(groupId -> {
            try {
                ps.setInt(1, id);
                ps.setInt(2,groupId);
                ps.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws IOException {
        NeoloveNameDifferentNationality neoloveNameDifferentNationality = new NeoloveNameDifferentNationality();
        neoloveNameDifferentNationality.loadDBDriver();
        neoloveNameDifferentNationality.update();
    }
}
