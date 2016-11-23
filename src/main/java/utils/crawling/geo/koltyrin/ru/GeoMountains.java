package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GeoMountains {
    private static final String insertSql = "insert into dic_mountains(mountain,country_flag_id,square_km2,length_km,width_km,peak_name,peak_height_m,continent) values (?,?,?,?,?,?,?,?)";

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

            Document doc = Jsoup.connect("http://geo.koltyrin.ru/gornye_sistemy.php").timeout(0).get();
            Elements elements = doc.select("html body div div div center table.list tbody");
            boolean flag = false;
            for (Element element : elements) {
                for (Element trChild : element.children()) {
                    if (flag) {
                        String mountain = trChild.child(0).text().trim();
                        String country = "";

                        for (Element img : trChild.child(1).children()) {
                            String src = img.attr("src").substring(8);
                            src = src.substring(0,src.length()-8);
                            country = country + src + " ";
                        }

                        country = country.trim();
                        country = country.replaceAll(" ", ",");

                        String square = trChild.child(2).text().trim().replaceAll(" ", "").replaceAll("\\?", "");
                        String length = trChild.child(3).text().trim().replaceAll(" ", "").replaceAll("\\?", "");
                        String width = trChild.child(4).text().trim().replaceAll(" ", "").replaceAll("\\?", "");
                        String peak = trChild.child(5).html().trim();
                        String peakName = peak.substring(0, peak.indexOf("<br>"));
                        String peakHeight = peak.substring(peak.indexOf("<br>") + 4, peak.length());
                        String continent = trChild.child(6).text().trim();
                        System.out.println("Горная система: " + mountain + "   Страна: " + country + "   Площадь: " + square + "   Длина: " + length + "   Ширина: " + width + "   Название вершины: " + peakName + "   Высота вершины: " + peakHeight + "   Континент: " + continent);
                        insert(insertPS, mountain, country, square, length, width, peakName, peakHeight, continent);
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

    private void insert(PreparedStatement ps, String mountain, String country, String square, String length, String width, String peakName, String peakHeight, String continent) throws SQLException {
        ps.setString(1, mountain);
        ps.setString(2, country);
        ps.setInt(3, square.equals("") ? new Integer(0) : new Integer(square));
        ps.setInt(4, length.equals("") ? new Integer(0) : new Integer(length));
        ps.setInt(5, width.equals("") ? new Integer(0) : new Integer(width));
        ps.setString(6, peakName);
        ps.setString(7, peakHeight);
        ps.setString(8, continent);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoMountains geoMountains = new GeoMountains();
        geoMountains.loadDBDriver();
        geoMountains.parseSite();
    }
}