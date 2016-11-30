package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GeoLakes {
    private static final String insertSql = "insert into dic_lakes(lake,country,square_km2,max_depth_m,avg_depth_m,sea_level_m,saltiness,flowing_river,continent) values (?,?,?,?,?,?,?,?,?)";

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

            Document doc = Jsoup.connect("http://geo.koltyrin.ru/ozera.php").timeout(0).get();
            //Elements elements = doc.select("html body div div div center table.list tbody");
            Elements elements = doc.select("html body div div div center table.list tbody");

            int flag = 0;
            for (Element element : elements) {
                for (Element trChild : element.children()) {
                    if (flag > 1) {
                        String lake = trChild.child(0).text().trim();
                        lake = lake.replaceAll(" $", "");
                        lake = lake.replaceAll(" $", "");
                        String country = "";
                        Elements imgs = trChild.child(0).children();
                        for (Element img : imgs) {
                            if ("img".equals(img.tagName())) {
                                String src = img.attr("src").substring(8);
                                src = src.substring(0, src.length() - 8);
                                country = country + src + " ";
                            }
                        }

                        country = country.trim();
                        country = country.replaceAll(" ", ",");

                        String square = trChild.child(1).text().trim().replaceAll(" ","");
                        String maxDepth = trChild.child(2).text().trim().replaceAll(" ", "").replaceAll("\\?", "");
                        String avgDepth = trChild.child(3).text().trim().replaceAll(" ", "").replaceAll("\\?", "");
                        String seaLevel = trChild.child(4).text().trim().replaceAll(" ", "").replaceAll("\\?", "");
                        String saltness = trChild.child(5).text().trim();
                        String flowingRiver = trChild.child(6).text().trim();
                        String continent = trChild.child(7).text().trim();
                        System.out.println("Название озера:" + lake + "|   Страна:" + country + "|   Площадь:" + square + "|   Макс. глубина:" + maxDepth + "|   Сред. глубина:" + avgDepth + "|   Уровень моря:" + seaLevel + "|   Соленность:" + saltness + "|   Вытекающая река:" + flowingRiver + "|   Континент:" + continent + "|");
                        insert(insertPS, lake, country, square, maxDepth, avgDepth, seaLevel, saltness, flowingRiver, continent);

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

    private void insert(PreparedStatement ps, String lake, String country, String square, String maxDepth, String avgDepth, String seaLevel, String saltness, String flowingRiver, String continent) throws SQLException {
        ps.setString(1, lake);
        ps.setString(2, country);
        ps.setInt(3, square.equals("") ? new Integer(0) : new Integer(square));
        ps.setInt(4, maxDepth.equals("") ? new Integer(0) : new Integer(maxDepth));
        ps.setInt(5, avgDepth.equals("") ? new Integer(0) : new Integer(avgDepth));
        ps.setInt(6, seaLevel.equals("") ? new Integer(0) : new Integer(seaLevel));
        ps.setString(7, saltness);
        ps.setString(8, flowingRiver);
        ps.setString(9, continent);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoLakes geoLakes = new GeoLakes();
        geoLakes.loadDBDriver();
        geoLakes.parseSite();
    }
}