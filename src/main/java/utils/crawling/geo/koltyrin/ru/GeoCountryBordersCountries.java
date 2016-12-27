package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

class GeoCountryBordersCountries {
    private static final String selectSql = "select id,flag_number from dic_countries";
    private static final String selectSeaSql = "select id,border from dic_countries";
    private static final String insertSql = "insert into dic_country_borders_countries(country_id,country_border_id) values (?,?)";

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
             PreparedStatement selectStraitPS = con.prepareStatement(selectSeaSql);
             PreparedStatement insertPS = con.prepareStatement(insertSql);
             ResultSet rs = selectPS.executeQuery();
             ResultSet countryRS = selectStraitPS.executeQuery()) {

            con.setAutoCommit(false);

            Map<Integer,Integer> countryMap = new HashMap<>();

            while(rs.next()) {
                int countryId = rs.getInt("id");
                int flagNumber = rs.getInt("flag_number");
                countryMap.put(flagNumber,countryId);
            }

            while(countryRS.next()) {
                int countryId = countryRS.getInt("id");
                String borderFlagIds = countryRS.getString("border");
                if (borderFlagIds != null && !borderFlagIds.isEmpty()) {
                    String[] borderIds = borderFlagIds.split(",");
                    for (int i = 0; i < borderIds.length; i++) {
                        if (borderIds[i] != null) {
                            System.out.println(countryId + "    " + new Integer(borderIds[i]));
                            insert(insertPS, countryId, countryMap.get(new Integer(borderIds[i])));
                        }
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


    private void insert(PreparedStatement ps, int contryId, int borderId) throws SQLException {
        ps.setInt(1, contryId);
        ps.setInt(2, borderId);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoCountryBordersCountries geoCountryBordersCountries = new GeoCountryBordersCountries();
        geoCountryBordersCountries.loadDBDriver();
        geoCountryBordersCountries.parseSite();
    }
}