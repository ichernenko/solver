package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GeoCountryBorders {
    private static final String selectSql = "select country from dic_countries order by country";
    private static final String insertSql = "update dic_countries set border=? where country=?";

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
             PreparedStatement updatePS = con.prepareStatement(insertSql);
             ResultSet rs = selectPS.executeQuery()) {

            con.setAutoCommit(false);

            while(rs.next()) {
                String country = rs.getString("country");
                System.out.println("--- " + country);
                Document doc = Jsoup.connect("http://geo.koltyrin.ru/country.php?country="+country).timeout(0).get();
                //Elements elements = doc.select("html body div div div.field_center div.wide div table tbody");
                Elements elements = doc.select("html body div div div.field_center div:nth-child(1) div.cell.cell50.bg_gray div:nth-child(2)");
                String border = "";
                for (Element element : elements) {
                    Elements imgs = element.children();
                    for(Element img: imgs) {
                        if ("img".equals(img.tagName()) && img.attr("title") !=null && !img.attr("title").contains(country)) {
                            String src = img.attr("src").substring(8);
                            src = src.substring(0, src.length() - 8);
                            border = border + src + " ";
                        }
                    }
                }
                elements = doc.select("body > div > div > div.field_center > div:nth-child(1) > div.cell.cell50.bg_gray > div:nth-child(2) > div");
                for (Element element : elements) {
                    if("с непризнанными странами:".equals(element.text())) {
                        Elements imgs = element.children();
                        for (Element img : imgs) {
                            if ("img".equals(img.tagName()) && img.attr("title") != null && !img.attr("title").contains(country)) {
                                String src = img.attr("src").substring(8);
                                src = src.substring(0, src.length() - 8);
                                border = border + src + " ";
                            }
                        }
                    }
                }
                border = border.trim();
                border = border.replaceAll(" ", ",");
                System.out.println("=== " + border);

                if(!border.trim().isEmpty()) {
                    update(updatePS, country, border);
                }

            }
            updatePS.executeBatch();
            con.commit();

        } catch (SQLException e) {
            System.out.println("Processing wasn't finished!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(PreparedStatement ps, String country, String border) throws SQLException {
        ps.setString(1, border);
        ps.setString(2, country);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoCountryBorders geoCountryBorders = new GeoCountryBorders();
        geoCountryBorders.loadDBDriver();
        geoCountryBorders.parseSite();
    }
}