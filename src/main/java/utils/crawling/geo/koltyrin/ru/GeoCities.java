package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GeoCities {
    private static final String selectSql = "select country from dic_countries order by country";
    private static final String insertSql = "insert into dic_cities(country,city,population,founded,region) values (?,?,?,?,?)";
    private static final int INSERTS_COUNT = 1000;

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
             PreparedStatement insertPS = con.prepareStatement(insertSql);
             ResultSet rs = selectPS.executeQuery()) {

            con.setAutoCommit(false);

            while(rs.next()) {

                String country = rs.getString("country");
                System.out.println(country);
                System.out.println("-------------------------------------------------");

                Document doc = Jsoup.connect("http://geo.koltyrin.ru/goroda.php?country="+country).timeout(0).get();
                Elements elements = doc.select("html body div div div.field_center div.wide div table tbody");

                for (Element element : elements) {
                    Elements trChilds = element.children();
                    trChilds.remove(0);
                    for (Element trChild : trChilds) {
                        String city = trChild.child(0).text();
                        String population = trChild.child(1).text();
                        String founded = trChild.child(2).text();
                        String region = trChild.child(3).text();
                        System.out.println("Страна: " + country +"   Название: " + city + "   Население: " + population + "   Основан: " + founded + "   Регион: " + region);
                        insert(insertPS, country, city.trim(), population.replaceAll(" ",""), founded.trim(), region.trim());
                    }
                }
                System.out.println();
            }
            insertPS.executeBatch();
            con.commit();

        } catch (SQLException e) {
            System.out.println("Processing wasn't finished!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insert(PreparedStatement ps, String country, String city, String population, String founded, String region) throws SQLException {
        ps.setString(1, country);
        ps.setString(2, city);
        ps.setInt(3, new Integer(population));
        ps.setString(4, founded);
        ps.setString(5, region);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoCities geoCities = new GeoCities();
        geoCities.loadDBDriver();
        geoCities.parseSite();
    }
}