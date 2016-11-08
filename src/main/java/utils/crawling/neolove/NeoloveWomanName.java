package utils.crawling.neolove;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class NeoloveWomanName {
    private static final String insertSql = "insert into dic_womens_name_ru (id, nationality, name, tag, derivative, frequency) values (?, ?, ?, ?, ?, ?)";
    private static final String deleteSql = "delete from dic_womens_name_ru where nationality=?";
    private static final String deleteAllSql = "delete from dic_womens_name_ru";
    private static final String currentIdSql = "select max(id) max_id from dic_womens_name_ru";
    private static final String SHORT_DERITATIVE = "Производн";
    private static final int SHORT_DERITATIVE_LENGTH = SHORT_DERITATIVE.length() + 4;
    private static final int INSERTS_COUNT = 1000;
    private static final String WOMEN_SIGN = "F";

    private void loadDBDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void parseSite(String nationalityFrom, String nationalityTo, int id) throws IOException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             PreparedStatement ps = con.prepareStatement(insertSql)){
            // con.setAutoCommit(false);

            int insertsCount = 0;
            Document doc = Jsoup.connect("http://names.neolove.ru/national/female/").timeout(0).get();
            Elements elements = doc.select("tbody tr td a");

            boolean isFoundNationalityFrom = false;
            for (Element element : elements) {
                String nationality = element.text().toLowerCase();
                nationality = nationality.substring(0, nationality.indexOf(' '));

                if (nationality.endsWith("ие")) {
                    nationality = nationality.substring(0, nationality.length() - 2) + "ое";
                }

                if (isFoundNationalityFrom == false) {
                    if (nationalityFrom != null && !nationalityFrom.equals(nationality)) {
                        continue;
                    } else {
                        isFoundNationalityFrom = true;
                    }
                }

                if (nationalityTo != null && nationalityTo.equals(nationality)) {
                    break;
                }


                Document nationalityMaleDoc = Jsoup.connect("http://names.neolove.ru" + element.attr("href")).timeout(0).get();
                Elements nationalityMaleElements = nationalityMaleDoc.select("tbody tr td div a");

                int j = 0;
                for (Element nationalityMaleElement : nationalityMaleElements) {
                    String name = nationalityMaleElement.text();
                    id++;

                    String derivative = null;
                    try {
                        Document nameMaleDoc = Jsoup.connect("http://names.neolove.ru" + element.attr("href") + nationalityMaleElement.attr("href")).timeout(0).get();
                        Elements nameMaleElements = nameMaleDoc.select("div.otstup");

                        for (Element nameMaleElement : nameMaleElements) {
                            int startDerivative = nameMaleElement.text().indexOf(SHORT_DERITATIVE);
                            if (startDerivative > 0) {
                                derivative = nameMaleElement.text();
                                int nextDerivative = derivative.indexOf(".", startDerivative + SHORT_DERITATIVE_LENGTH);
                                if (nextDerivative > 0) {
                                    int endDerivative = derivative.indexOf(".", nextDerivative);
                                    if (endDerivative > 0) {
                                        derivative = derivative.substring(startDerivative + SHORT_DERITATIVE_LENGTH, endDerivative);
                                    } else {
                                        derivative = derivative.substring(startDerivative + SHORT_DERITATIVE_LENGTH, nextDerivative);
                                    }
                                }
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Не найдена страница: " + "http://names.neolove.ru" + element.attr("href") + nationalityMaleElement.attr("href"));
                    }
                    System.out.println(id + "   " + nationality + "   " + name + "   " + WOMEN_SIGN + "   " + derivative);
                    insert(ps, id, nationality, name, WOMEN_SIGN, derivative);
                    insertsCount++;
                    if (insertsCount == INSERTS_COUNT) {
                        ps.executeBatch();
                        insertsCount = 0;
                    }
                }
            }
            if (insertsCount > 0) {
                ps.executeBatch();
            }

        } catch (SQLException e) {
            System.out.println("Processing wasn't finished!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insert(PreparedStatement ps, int i, String nationality, String name, String tag, String derivative) throws SQLException {
        ps.setInt(1, i);
        ps.setString(2, nationality);
        ps.setString(3, name);
        ps.setString(4, tag);
        ps.setString(5, derivative);
        ps.setInt(6, 0);
        ps.addBatch();
    }

    private void delete(String nationalityFrom) {
        if (nationalityFrom != null) {
            // Удаление указанной национальности (обычно последней скаченной)
            try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
                 PreparedStatement ps = con.prepareStatement(deleteSql)) {
                ps.setString(1, nationalityFrom);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Удаление всех записей
            try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
                 PreparedStatement ps = con.prepareStatement(deleteAllSql)) {
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getCurrentId() {
        int id = 0;
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             PreparedStatement ps = con.prepareStatement(currentIdSql);
             ResultSet rs = ps.executeQuery()) {
            if(rs.next()) {
                id = rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void main(String[] args) throws IOException {
        NeoloveWomanName neoloveWomanName = new NeoloveWomanName();
        neoloveWomanName.loadDBDriver();

        String nationalityFrom = null; // "русское";
        String nationalityTo = null; // "сабинское";

        neoloveWomanName.delete(nationalityFrom);
        neoloveWomanName.parseSite(nationalityFrom, nationalityTo, neoloveWomanName.getCurrentId());
    }
}