package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GeoCaves {
    private static final String insertSql = "insert into dic_caves(cave,country,depth_m,length_m) values (?,?,?,?)";

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

            Document doc = Jsoup.connect("http://geo.koltyrin.ru/peschery.php").timeout(0).get();
            //Elements elements = doc.select("html body div div div center table.list tbody");
            Elements elements = doc.select("html body div div div center table.list tbody");

            int flag = 0;
            for (Element element : elements) {
                for (Element trChild : element.children()) {
                    if (flag > 0) {
                        String cave = trChild.child(0).text().trim();
                        cave = cave.replaceAll(" $", "");
                        cave = cave.replaceAll(" $", "");
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

                        String depth = trChild.child(2).text().trim().replaceAll(" ","").replaceAll("\\?", "");
                        String length = trChild.child(3).text().trim().replaceAll(" ","").replaceAll("\\?", "");
                        System.out.println("Название пещеры:" + cave + "|   Страна:" + country + "|   Глубина:" + depth + "|   Длина:" + length + "|");
                        insert(insertPS, cave, country, depth, length);

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

    private void insert(PreparedStatement ps, String cave, String country, String depth, String length) throws SQLException {
        ps.setString(1, cave);
        ps.setString(2, country);
        if (!depth.equals("")) {
            ps.setInt(3, new Integer(depth));
        } else {
            ps.setNull(3, Types.INTEGER);
        }
        if (!length.equals("")) {
            ps.setInt(4, new Integer(length));
        } else {
            ps.setNull(4, Types.INTEGER);
        }
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoCaves geoCaves = new GeoCaves();
        geoCaves.loadDBDriver();
        geoCaves.parseSite();
    }
}