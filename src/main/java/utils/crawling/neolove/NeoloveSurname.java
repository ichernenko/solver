package utils.crawling.neolove;

import java.io.IOException;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class NeoloveSurname {
    private static final String insertSql = "insert into dic_surnames_ru (surname) values (?)";
    private static final int INSERTS_COUNT = 1000;

    private void loadDBDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void parseSite() throws IOException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             PreparedStatement ps = con.prepareStatement(insertSql)) {
             con.setAutoCommit(false);

            int insertsCount = 0;
            int id = 0;

            for (int i = 1; i < 30; i++) {
                Document doc = Jsoup.connect("http://names.neolove.ru/last_names/" + i + "/").timeout(0).get();
                Elements elements = doc.select("div div.l_main_width div.b-container-wrapper div.b-container-container div.g-fullwidth div div.b-center-wrapper div.col-center index div div.list_names table tbody");

                for (Element element : elements) {
                    for (Element trChild : element.children()) {
                        for (Element tdChild : trChild.children()) {
                            if (!" ".equals(tdChild.text())) {
                                String subLetterHref = tdChild.child(0).child(0).attr("href");
                                int pageID = 0;
                                String oldFirstSurname = null;
                                boolean isContinued = true;
                                while (isContinued) {
                                    pageID++;
                                    Document subLetterDoc = Jsoup.connect("http://names.neolove.ru" + subLetterHref + "?pageID=" + pageID).timeout(0).get();
                                    Elements subLetterElements = subLetterDoc.select("div div.l_main_width div.b-container-wrapper div.b-container-container div.g-fullwidth div div.b-center-wrapper div.col-center index div div.list_names table tbody");
                                    if (subLetterElements.size() < 2) {
                                        isContinued = false;
                                    } else {
                                        Element subLetterElement = subLetterElements.get(1);
                                        if (subLetterElement != null) {
                                            Elements trChild2s = subLetterElement.children();
                                            if (trChild2s != null) {
                                                String surname = null;
                                                for (Element trChild2 : trChild2s) {
                                                    Elements tdChild2s = trChild2.children();
                                                    if (tdChild2s != null) {
                                                        for (Element tdChild2 : tdChild2s) {
                                                            if (surname == null) {
                                                                surname = tdChild2.text();
                                                                if (surname.equals(oldFirstSurname)) {
                                                                    isContinued = false;
                                                                    break;
                                                                }
                                                                oldFirstSurname = surname;
                                                                insert(ps, surname);
                                                                insertsCount++;
                                                                if (insertsCount == INSERTS_COUNT) {
                                                                    ps.executeBatch();
                                                                    insertsCount = 0;
                                                                }
                                                                System.out.println(pageID + ".---- " + surname + "   " + ++id);
                                                            } else {
                                                                surname = tdChild2.text();
                                                                if (!" ".equals(surname)) {
                                                                    insert(ps, surname);
                                                                    insertsCount++;
                                                                    if (insertsCount == INSERTS_COUNT) {
                                                                        ps.executeBatch();
                                                                        insertsCount = 0;
                                                                    }
                                                                    System.out.println(pageID + ".---- " + surname + "   " + ++id);
                                                                }

                                                            }
                                                        }
                                                    }
                                                    if (!isContinued) {
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
            if (insertsCount > 0) {
                ps.executeBatch();
            }
            con.commit();

        } catch (SQLException e) {
            System.out.println("Processing wasn't finished!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insert(PreparedStatement ps, String surname) throws SQLException {
        ps.setString(1, surname);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        NeoloveSurname neoloveSurname = new NeoloveSurname();
        neoloveSurname.loadDBDriver();
        neoloveSurname.parseSite();
    }
}