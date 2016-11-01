package utils.сrawling.morpher;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.net.URLEncoder;

class Morpher {

    private Document doc = null;

    public Morpher(String phrase) throws
            IOException,
            SAXException,
            ParserConfigurationException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String url = "http://api.morpher.ru/WebService.asmx/GetXml?s=";
        String uRLEncodedPhrase = URLEncoder.encode(phrase, "UTF-8");
        doc = builder.parse(url + uRLEncodedPhrase);
    }

    public String getCase(String padeg) {
        String result = "";
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();
        for (int x = 0; x < nodes.getLength(); x++) {
            Node item = nodes.item(x);
            if (item instanceof Element) {
                Element el = (Element)item;
                if (el.getTagName().equals(padeg)) {
                    result = ((Text)el.getFirstChild()).getData();
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws java.lang.Exception {
        String phrase = "пилот международной космической станции";
        Morpher morpher = new Morpher(phrase);
        String padeg = "Т"; // русская буква Т - творительный
        System.out.println("И: " + phrase);
        System.out.println(padeg + ": " + morpher.getCase(padeg));
    }
}