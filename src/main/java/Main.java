import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import textStructureDefinition.Sentence;
import textStructureDefinition.TextParser;
import textStructureDefinition.Word;

import java.util.List;

public class Main {
    public static void main(String... args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("rmi-client-beans.xml");
        TextParser textParser = (TextParser)context.getBean("textParserBean");
        List<Sentence> sentences = textParser.getSentences("самое темное время суток перед рассветом");
        sentences.forEach(m -> {
            List<Word> words = m.getWords();
            words.forEach(k -> {
                        System.out.println(k.getWord());
                        for (int i = 0; i < k.getWordTags().length; i++) {
                            System.out.printf("\t%-30s", k.getWordTags()[i].getWordTag());
                            System.out.print("\t\t" + (k.getWordTags()[i].getLemma())
                                    + "\t\t" + k.getWordTags()[i].getLemmaTag());
                            System.out.println();
                        }
                    });
                });
//        words = textParser.getWords("мальчик нашел шесть яблок");
//        words.forEach(k-> {System.out.println(k.getWord());
//            for(int i = 0; i < k.getWordTags().length; i ++) {
//                System.out.printf("\t%-30s", k.getWordTags()[i].getWordTag());
//                System.out.print("\t\t" + (k.getWordTags()[i].getLemma())
//                        + "\t\t" + k.getWordTags()[i].getLemmaTag());
//                System.out.println();
//            }
//        });
    }
}
