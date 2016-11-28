package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GeoSeas {
    private static final String insertSql = "insert into dic_seas(sea,country,square_km2,max_depth,ocean) values (?,?,?,?,?)";

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

            Document doc = Jsoup.connect("http://geo.koltyrin.ru/morja.php").timeout(0).get();
            //Elements elements = doc.select("html body div div div center table.list tbody");
            Elements elements = doc.select("html body div div div.field_center div table.list tbody");

            boolean flag = false;
            for (Element element : elements) {
                for (Element trChild : element.children()) {
                    if (flag) {
                        String sea = trChild.child(0).text().trim();
                        String country = "";

                        for (Element img : trChild.child(1).children()) {
                            String src = img.attr("src").substring(8);
                            src = src.substring(0,src.length()-8);
                            country = country + src + " ";
                        }

                        country = country.trim();
                        country = country.replaceAll(" ", ",");

                        String square = trChild.child(2).text().trim().replaceAll(" ", "").replaceAll("\\?", "");
                        String depth = trChild.child(3).text().trim().replaceAll(" ", "").replaceAll("\\?", "");
                        String ocean = trChild.child(4).html().trim();
                        System.out.println("Море: " + sea + "   Страна: " + country + "   Площадь: " + square + "   Глубина: " + depth + "   Океан: " + ocean);
                        insert(insertPS, sea, country, square, depth, ocean);
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

    private void insert(PreparedStatement ps, String sea, String country, String square, String depth, String ocean) throws SQLException {
        ps.setString(1, sea);
        ps.setString(2, country);
        ps.setInt(3, square.equals("") ? new Integer(0) : new Integer(square));
        ps.setInt(4, depth.equals("") ? new Integer(0) : new Integer(depth));
        ps.setString(5, ocean);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoSeas geoSeas = new GeoSeas();
        geoSeas.loadDBDriver();
        geoSeas.parseSite();
    }
}