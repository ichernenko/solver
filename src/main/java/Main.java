import morphologicAnalysis.MorphologicAnalysis;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import morphologicAnalysis.Sentence;
import morphologicAnalysis.Word;

import java.util.List;

public class Main {
    public static void main(String... args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("rmi-client-beans.xml");
        MorphologicAnalysis morphologicAnalysis = (MorphologicAnalysis)context.getBean("textParserBean");
        List<Sentence> sentences = morphologicAnalysis.getSentences("самое темное время суток перед рассветом");
        sentences.forEach(m -> {
            List<Word> words = m.getWords();
            words.forEach(k -> {
                        System.out.println(k.getWord());
                        for (int i = 0; i < k.getWordTags().length; i++) {
//                            System.out.printf("\t%-30s", k.getWordTags()[i].getWordTag());
//                            System.out.print("\t\t" + (k.getWordTags()[i].getLemma())
//                                    + "\t\t" + k.getWordTags()[i].getLemmaTag());
                            System.out.println();
                        }
                    });
                });
//        words = morphologicAnalysis.getWords("мальчик нашел шесть яблок");
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
