package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

class GeoMountainsCountries {
    private static final String selectSql = "select id,flag_number from dic_countries";
    private static final String selectMountainSql = "select id,country_flag_id from dic_mountains";
    private static final String insertSql = "insert into dic_mountains_countries(mountain_id,country_id) values (?,?)";

    private void loadDBDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void parseSite() throws IOException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             PreparedStatement selectPS = con.prepareStatement(selectSql);
             PreparedStatement selectMountainPS = con.prepareStatement(selectMountainSql);
             PreparedStatement insertPS = con.prepareStatement(insertSql);
             ResultSet rs = selectPS.executeQuery();
             ResultSet mountainRS = selectMountainPS.executeQuery()) {

            con.setAutoCommit(false);

            Map<Integer,Integer> countryMap = new HashMap<>();

            while(rs.next()) {
                int countryId = rs.getInt("id");
                int flagNumber = rs.getInt("flag_number");
                countryMap.put(flagNumber,countryId);
            }

            while(mountainRS.next()) {
                int mountainId = mountainRS.getInt("id");
                String countryFlagIds = mountainRS.getString("country_flag_id");
                String[] countryIds = countryFlagIds.split(",");
                for (int i = 0; i < countryIds.length; i ++) {
                    System.out.println(mountainId + "    " + new Integer(countryIds[i]));
                    insert(insertPS, mountainId, countryMap.get(new Integer(countryIds[i])));
                }
            }

            insertPS.executeBatch();
            con.commit();

        } catch (SQLException e) {
            System.out.println("Processing wasn't finished!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void insert(PreparedStatement ps, int mountain_id, int country_id) throws SQLException {
        ps.setInt(1, mountain_id);
        ps.setInt(2, country_id);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoMountainsCountries geoMountainsCountries = new GeoMountainsCountries();
        geoMountainsCountries.loadDBDriver();
        geoMountainsCountries.parseSite();
    }
}