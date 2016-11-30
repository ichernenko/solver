package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

class GeoVolcanoesCountries {
    private static final String selectSql = "select id,flag_number from dic_countries";
    private static final String selectSeaSql = "select id,country from dic_volcanoes";
    private static final String insertSql = "insert into dic_volcanoes_countries(volcano_id,country_id) values (?,?)";

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
             ResultSet straitRS = selectStraitPS.executeQuery()) {

            con.setAutoCommit(false);

            Map<Integer,Integer> countryMap = new HashMap<>();

            while(rs.next()) {
                int countryId = rs.getInt("id");
                int flagNumber = rs.getInt("flag_number");
                countryMap.put(flagNumber,countryId);
            }

            while(straitRS.next()) {
                int straitId = straitRS.getInt("id");
                String countryFlagIds = straitRS.getString("country");
                if (countryFlagIds != null && !countryFlagIds.isEmpty()) {
                    String[] countryIds = countryFlagIds.split(",");
                    for (int i = 0; i < countryIds.length; i++) {
                        if (countryIds[i] != null) {
                            System.out.println(straitId + "    " + new Integer(countryIds[i]));
                            insert(insertPS, straitId, countryMap.get(new Integer(countryIds[i])));
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


    private void insert(PreparedStatement ps, int seaId, int countryId) throws SQLException {
        ps.setInt(1, seaId);
        ps.setInt(2, countryId);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoVolcanoesCountries geoVolcanoesCountries = new GeoVolcanoesCountries();
        geoVolcanoesCountries.loadDBDriver();
        geoVolcanoesCountries.parseSite();
    }
}