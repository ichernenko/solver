package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GeoPeaks {
    private static final String insertSql = "insert into dic_peaks(peak,country,height_m,mountain,continent) values (?,?,?,?,?)";

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

            for (int i = 1; i < 15; i++) {
                Document doc = Jsoup.connect("http://geo.koltyrin.ru/gory.php?page=" + i).timeout(0).get();
                //Elements elements = doc.select("html body div div div center table.list tbody");
                Elements elements = doc.select("html body div div div center table.list tbody");

                int flag = 0;
                for (Element element : elements) {
                    for (Element trChild : element.children()) {
                        if (flag > 0) {
                            String peak = trChild.child(0).text().trim();
                            peak = peak.replaceAll(" $", "");
                            peak = peak.replaceAll(" $", "");
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

                            String height = trChild.child(2).text().trim().replaceAll(" ", "").replaceAll("\\?", "");
                            String mountain = trChild.child(3).text().trim();
                            String continent = trChild.child(4).text().trim();
                            System.out.println("Название пустыни:" + peak + "|   Страна:" + country + "|   Высота:" + height + "|   Горы:" + mountain + "|   Континент:" + continent + "|");
                            insert(insertPS, peak, country, height, mountain, continent);

                        } else {
                            flag++;
                        }
                    }
                    System.out.println();
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

    private void insert(PreparedStatement ps, String peak, String country, String height, String mountain, String continent) throws SQLException {
        ps.setString(1, peak);
        ps.setString(2, country);
        if (!height.equals("")) {
            ps.setInt(3, new Integer(height));
        } else {
            ps.setNull(3, Types.INTEGER);
        }
        ps.setString(4, mountain);
        ps.setString(5, continent);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoPeaks geoPeaks = new GeoPeaks();
        geoPeaks.loadDBDriver();
        geoPeaks.parseSite();
    }
}