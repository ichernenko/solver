package utils.crawling.geo.koltyrin.ru;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GeoStraits {
    private static final String insertSql = "insert into dic_straits(strait,country,length_km,depth_m,width_from_km,width_to_km,connection1,connection2,separation1,separation2,ocean) values (?,?,?,?,?,?,?,?,?,?,?)";

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

            Document doc = Jsoup.connect("http://geo.koltyrin.ru/prolivy.php").timeout(0).get();
            //Elements elements = doc.select("html body div div div center table.list tbody");
            Elements elements = doc.select("html body div div div center table.list tbody");

            boolean flag = false;
            for (Element element : elements) {
                for (Element trChild : element.children()) {
                    if (flag) {
                        String strait = trChild.child(0).text().trim();
                        strait = strait.replaceAll(" $", "");
                        strait = strait.replaceAll(" $", "");
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

                        String length = trChild.child(1).text().trim().replaceAll(" ","");
                        String depth = trChild.child(2).text().trim().replaceAll(" ", "").replaceAll("\\?", "");
                        String width = trChild.child(3).html();
                        String widthFrom = width.substring(0, width.indexOf("<br>"));
                        widthFrom = widthFrom.substring(3);
                        widthFrom = widthFrom.replaceAll(" ","");
                        String widthTo = width.substring(width.indexOf("<br>") + 4, width.length());
                        widthTo = widthTo.substring(3).replaceAll("\\?", "").replaceAll(" ","");

                        String connection = trChild.child(4).html();
                        String connection1, connection2;
                        if (connection.indexOf("<br>")>0) {
                            connection1 = connection.substring(0, connection.indexOf("<br>"));
                            connection2 = connection.substring(connection.indexOf("<br>") + 4, connection.length() - 4);
                        } else {
                            connection1 = connection.trim();
                            connection2 = null;
                        }

                        String separation = trChild.child(5).html();
                        String separation1 = separation.substring(0, separation.indexOf("<br>"));
                        String separation2 = separation.substring(separation.indexOf("<br>") + 4, separation.length() - 4);

                        String ocean = trChild.child(6).text().trim();
                        System.out.println("Название пролива:" + strait + "|   Страна:" + country + "|   Длина:" + length + "|   Глубина:" + depth + "|   Ширина от:" + widthFrom + "|   Ширина до:" + widthTo + "|   Соединение1:" + connection1 + "|   Соединение2:" + connection2 + "|   Разъединяет1:" + separation1 + "|   Разъединяет2:" + separation2 + "|   Океан:" + ocean + "|");
                        insert(insertPS, strait, country, length, depth, widthFrom, widthTo, connection1, connection2, separation1, separation2, ocean);

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

    private void insert(PreparedStatement ps, String strait, String country, String length, String depth, String widthFrom, String widthTo, String connection1, String connection2, String separation1, String separation2, String ocean) throws SQLException {
        ps.setString(1, strait);
        ps.setString(2, country);
        ps.setInt(3, length.equals("") ? new Integer(0) : new Integer(length));
        ps.setInt(4, depth.equals("") ? new Integer(0) : new Integer(depth));
        ps.setDouble(5, widthFrom.equals("") ? new Double(0) : new Double(widthFrom));
        ps.setDouble(6, widthTo.equals("") ? new Double(0) : new Double(widthTo));
        ps.setString(7, connection1);
        ps.setString(8, connection2);
        ps.setString(9, separation1);
        ps.setString(10, separation2);
        ps.setString(11, ocean);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        GeoStraits geoStraits = new GeoStraits();
        geoStraits.loadDBDriver();
        geoStraits.parseSite();
    }
}