package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GeoWaterfalls {
    private static final String insertSql = "insert into dic_waterfalls(waterfall,country,height_m,river,continent) values (?,?,?,?,?)";

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

            Document doc = Jsoup.connect("http://geo.koltyrin.ru/vodopady.php").timeout(0).get();
//            Elements elements = doc.select("html body div div div center table.list tbody");
            Elements elements = doc.select("html body div div div center table.list tbody");

            boolean flag = false;
            for (Element element : elements) {
                for (Element trChild : element.children()) {
                    if (flag) {
                        String waterfall = trChild.child(0).text().trim();
                        waterfall = waterfall.replaceAll(" $", "");
                        waterfall = waterfall.replaceAll(" $", "");

                        String country = "";
                        Elements imgs = trChild.child(1).children();
                        for (Element img : imgs) {
                            if ("img".equals(img.tagName())) {
                                String src = img.attr("src").substring(7);
                                src = src.substring(0, src.length() - 8);
                                country = country + src + " ";
                            }
                        }

                        country = country.trim();
                        country = country.replaceAll(" ", ",");

                        String height = trChild.child(2).text().replaceAll(" ","");
                        String river = trChild.child(3).text().trim();
                        String continent = trChild.child(4).text().trim();

                        System.out.println("Название водопада:" + waterfall + "|   Страна:" + country + "|   Высота:" + height + "|   Река:" + river + "|   Континент:" + continent + "|");
                        insert(insertPS, waterfall, country, height, river, continent);

                    } else {
                        flag = true;
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

    private void insert(PreparedStatement ps, String waterfall, String country, String height, String river, String continent) throws SQLException {
        ps.setString(1, waterfall);
        ps.setString(2, country);
        ps.setInt(3, height.equals("") ? new Integer(0) : new Integer(height));
        ps.setString(4, river);
        ps.setString(5, continent);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoWaterfalls geoWaterfalls = new GeoWaterfalls();
        geoWaterfalls.loadDBDriver();
        geoWaterfalls.parseSite();
    }
}