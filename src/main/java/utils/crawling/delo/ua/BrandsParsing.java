package utils.crawling.delo.ua;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class BrandsParsing {
    private static final String insertSql = "insert into dic_brands_ua(brand,industry_description) values (?,?)";

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

            Document doc = Jsoup.connect("https://delo.ua/rates/brands-rating/brand-sectors/").timeout(0).get();
            //Elements elements = doc.select("html body div div div center table.list tbody");
            Elements elements = doc.select("html body div div div div div table tbody tr td div.label");


            for (Element element : elements) {


                String brand = element.child(0).text();
                String industry = element.child(1).text();
                System.out.println("Название бренда:" + brand + "|   Вид деятельности:" + industry + "|");
                insert(insertPS, brand, industry);
            }
            insertPS.executeBatch();
            con.commit();

        } catch (SQLException e) {
            System.out.println("Processing wasn't finished!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insert(PreparedStatement ps, String brand, String industry) throws SQLException {
        ps.setString(1, brand);
        ps.setString(2, industry);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        BrandsParsing brandsParsing = new BrandsParsing();
        brandsParsing.loadDBDriver();
        brandsParsing.parseSite();
    }
}