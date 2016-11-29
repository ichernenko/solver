package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

class GeoRiversCountries {
    private static final String selectSql = "select id,flag_number from dic_countries";
    private static final String selectRiverSql = "select id,country from dic_rivers";
    private static final String insertSql = "insert into dic_rivers_countries(river_id,country_id) values (?,?)";

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
             PreparedStatement selectRiverPS = con.prepareStatement(selectRiverSql);
             PreparedStatement insertPS = con.prepareStatement(insertSql);
             ResultSet rs = selectPS.executeQuery();
             ResultSet riverRS = selectRiverPS.executeQuery()) {

            con.setAutoCommit(false);

            Map<Integer,Integer> countryMap = new HashMap<>();

            while(rs.next()) {
                int countryId = rs.getInt("id");
                int flagNumber = rs.getInt("flag_number");
                countryMap.put(flagNumber,countryId);
            }

            while(riverRS.next()) {
                int riverId = riverRS.getInt("id");
                String countryFlagIds = riverRS.getString("country");
                if (countryFlagIds != null && !countryFlagIds.isEmpty()) {
                    String[] countryIds = countryFlagIds.split(",");
                    for (int i = 0; i < countryIds.length; i++) {
                        System.out.println(riverId + "    " + new Integer(countryIds[i]));
                        insert(insertPS, riverId, countryMap.get(new Integer(countryIds[i])));
                    }
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


    private void insert(PreparedStatement ps, int riverId, int countryId) throws SQLException {
        ps.setInt(1, riverId);
        ps.setInt(2, countryId);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoRiversCountries geoRiversCountries = new GeoRiversCountries();
        geoRiversCountries.loadDBDriver();
        geoRiversCountries.parseSite();
    }
}