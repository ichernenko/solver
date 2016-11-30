package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GeoVolcanoes {
    private static final String insertSql = "insert into dic_volcanoes(volcano,country,height_m,volcano_form,last_eruption,location,continent) values (?,?,?,?,?,?,?)";

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

            Document doc = Jsoup.connect("http://geo.koltyrin.ru/vulkany.php").timeout(0).get();
//            Elements elements = doc.select("html body div div div center table.list tbody");
            Elements elements = doc.select("html body div div div div table.list tbody");

            boolean flag = false;
            for (Element element : elements) {
                for (Element trChild : element.children()) {
                    if (flag) {
                        String volcano = trChild.child(0).text().trim();
                        volcano = volcano.replaceAll(" $", "");
                        volcano = volcano.replaceAll(" $", "");

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

                        String height = trChild.child(2).text().replaceAll(" ","");
                        String volcanoForm = trChild.child(3).text().trim();
                        String lastEruption = trChild.child(4).text().trim();
                        String location = trChild.child(5).text().trim();
                        String continent = trChild.child(6).text().trim();

                        System.out.println("Название вулкана:" + volcano + "|   Страна:" + country + "|   Высота:" + height + "|   Форма вулкана:" + volcanoForm + "|   Последнее извержение:" + lastEruption + "|   Местоположение:" + location + "|   Континент:" + continent + "|");
                        insert(insertPS, volcano, country, height, volcanoForm, lastEruption, location, continent);

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

    private void insert(PreparedStatement ps, String volcano, String country, String height, String volcanoForm, String lastEruption, String location, String continent) throws SQLException {
        ps.setString(1, volcano);
        ps.setString(2, country);
        ps.setInt(3, height.equals("") ? new Integer(0) : new Integer(height));
        ps.setString(4, volcanoForm);
        ps.setString(5, lastEruption);
        ps.setString(6, location);
        ps.setString(7, continent);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoVolcanoes geoVolcanoes = new GeoVolcanoes();
        geoVolcanoes.loadDBDriver();
        geoVolcanoes.parseSite();
    }
}