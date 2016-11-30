package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GeoDeserts {
    private static final String insertSql = "insert into dic_deserts(desert,country,square_km2,type,min_temperature_c,max_temperature_c,temperature_difference_c,precipitation_mm,continent) values (?,?,?,?,?,?,?,?,?)";

    private void loadDBDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void parseSite() throws IOException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             PreparedStatement insertPS = con.prepareStatement(insertSql)) {

            con.setAutoCommit(false);

            Document doc = Jsoup.connect("http://geo.koltyrin.ru/pustyni.php").timeout(0).get();
            //Elements elements = doc.select("html body div div div center table.list tbody");
            Elements elements = doc.select("html body div div div center table.list tbody");

            int flag = 0;
            for (Element element : elements) {
                for (Element trChild : element.children()) {
                    if (flag > 1) {
                        String desert = trChild.child(0).text().trim();
                        desert = desert.replaceAll(" $", "");
                        desert = desert.replaceAll(" $", "");
                        String country = "";
                        Elements imgs = trChild.child(1).children();
                        for (Element img : imgs) {
                            if ("img".equals(img.tagName())) {
                                String src = img.attr("src").substring(8);
                                src = src.substring(0, src.length() - 8);
                                country = country + src + " ";
                            }
                        }

                        country = country.trim();
                        country = country.replaceAll(" ", ",");

                        String square = trChild.child(2).text().trim().replaceAll(" ","").replaceAll("\\?", "");
                        String type = trChild.child(3).text().trim();
                        String minTemperature = trChild.child(4).text().trim();
                        String maxTemperature = trChild.child(5).text().trim();
                        String temperatureDifference = trChild.child(6).text().trim();
                        String precipitation = trChild.child(7).text().trim();
                        String continent = trChild.child(8).text().trim();
                        System.out.println("Название пустыни:" + desert + "|   Страна:" + country + "|   Площадь:" + square + "|   Тип:" + type + "|   Мин. t:" + minTemperature + "|   Макс. t:" + maxTemperature + "|   Перепад:" + temperatureDifference + "|   Осадки:" + precipitation + "|   Континент:" + continent + "|");
                        insert(insertPS, desert, country, square, type, minTemperature, maxTemperature, temperatureDifference, precipitation, continent);

                    } else {
                        flag ++;
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

    private void insert(PreparedStatement ps, String desert, String country, String square, String type, String minTemperature, String maxTemperature, String temperatureDifference, String precipitation, String continent) throws SQLException {
        ps.setString(1, desert);
        ps.setString(2, country);
        ps.setInt(3, square.equals("") ? new Integer(0) : new Integer(square));
        ps.setString(4, type);
        ps.setString(5, minTemperature);
        ps.setString(6, maxTemperature);
        ps.setString(7, temperatureDifference);
        ps.setString(8, precipitation);
        ps.setString(9, continent);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoDeserts geoDeserts = new GeoDeserts();
        geoDeserts.loadDBDriver();
        geoDeserts.parseSite();
    }
}