package utils.crawling.neolove;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Neolove {
    public void parseSite()  throws IOException {

        Document doc = Jsoup.connect("http://names.neolove.ru/national/male/").get();
        Elements elements = doc.select("tbody tr td a");

        int i = 0;
        for (Element e : elements) {
//            if (i > 3) {
//                break;
//            }

            String national = e.text();
            national = national.substring(0, national.indexOf(' ')).toLowerCase();
            if (national.endsWith("ие")) {
                national = national.substring(0, national.length() - 2) + "ое";
            }

            if (!"русское".equals(national)) {
                continue;
            }


            System.out.println("-----------------------------------------------------------");
            System.out.println(national + "      "); //  + e.attr("href")
            System.out.println("-----------------------------------------------------------");

            Document nationalMaleDoc = Jsoup.connect("http://names.neolove.ru" + e.attr("href")).get();
            Elements nationalMaleElements = nationalMaleDoc.select("tbody tr td div a");


            int j = 0;
            for (Element nationalMaleElement : nationalMaleElements) {
//                if (j > 3) {
//                    break;
//                }

                String name = nationalMaleElement.text();

//                if (!"Павел".equals(name)) {
//                    continue;
//                }

                System.out.print("          " + ++i + ". " + name); //  + nationalMaleElement.attr("href")

                Document nameMaleDoc = Jsoup.connect("http://names.neolove.ru" + e.attr("href") + nationalMaleElement.attr("href")).get();
                Elements nameMaleElements = nameMaleDoc.select("div.otstup");

                for (Element nameMaleElement : nameMaleElements) {
                    String derivative = nameMaleElement.text();
                    int startDerivative = derivative.indexOf("Производн");
                    if (startDerivative > 0){
                        int nextDerivative = derivative.indexOf(".", startDerivative);
                        if (nextDerivative > 0) {
                            int endDerivative = derivative.indexOf(".", nextDerivative);
                            if (endDerivative > 0) {
                                derivative = derivative.substring(startDerivative + 13, endDerivative);
                            } else {
                                derivative = derivative.substring(startDerivative + 13, nextDerivative);
                            }
                        }
                        System.out.print("                    (" + derivative + ")");
                        break;
                    }
                }
                System.out.println();
            }
        }

//        //print all available links on page
//        Elements links = doc.select("a[href]");
//        for(Element l: links){
//            System.out.println("link: " +l.attr("abs:href"));
//        }
    }

    public static void main(String[] args) throws IOException {
        Neolove neolove = new Neolove();
        neolove.parseSite();
    }
}