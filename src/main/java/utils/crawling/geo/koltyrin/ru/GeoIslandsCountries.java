package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

class GeoIslandsCountries {
    private static final String selectSql = "select id,country from dic_countries";
    private static final String selectIslandSql = "select id,country from dic_islands";
    private static final String insertSql = "insert into dic_islands_countries(island_id,country_id) values (?,?)";

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
             PreparedStatement selectIslandPS = con.prepareStatement(selectIslandSql);
             PreparedStatement insertPS = con.prepareStatement(insertSql);
             ResultSet rs = selectPS.executeQuery();
             ResultSet islandRS = selectIslandPS.executeQuery()) {

            con.setAutoCommit(false);

            Map<String,Integer> countryMap = new HashMap<>();

            while(rs.next()) {
                int countryId = rs.getInt("id");
                String country = rs.getString("country");
                countryMap.put(country,countryId);
            }

            while(islandRS.next()) {
                int islandId = islandRS.getInt("id");
                String countries = islandRS.getString("country");
                String[] countryIds = countries.split(",");
                for (int i = 0; i < countryIds.length; i ++) {
                    System.out.println(islandId + "    " + countryIds[i].trim());
                    insert(insertPS, islandId, countryMap.get(countryIds[i].trim()));
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


    private void insert(PreparedStatement ps, int islandId, int countryId) throws SQLException {
        ps.setInt(1, islandId);
        ps.setInt(2, countryId);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoIslandsCountries geoIslandsCountries = new GeoIslandsCountries();
        geoIslandsCountries.loadDBDriver();
        geoIslandsCountries.parseSite();
    }
}