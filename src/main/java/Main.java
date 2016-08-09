import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {
    public static void main(String... args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("rmi-client-beans.xml");
        TextParser textParser = (TextParser)context.getBean("textParserBean");
        List<Word> wordList = textParser.getWordList("само1е темное время суток перед рассветом");
        wordList.forEach(k-> {System.out.println(k.getWord());
                for(int i = 0; i < k.getWordTagArray().length; i ++) {
                    System.out.printf("\t%-30s", k.getWordTagArray()[i].getWordTag());
                    System.out.print("\t\t" + (k.getWordTagArray()[i].getLemma())
                            + "\t\t" + k.getWordTagArray()[i].getLemmaTag());
                    System.out.println();
                }
            });
//        wordList = textParser.getWordList("мальчик нашел шесть яблок");
//        wordList.forEach(k-> {System.out.println(k.getWord());
//            for(int i = 0; i < k.getWordTagArray().length; i ++) {
//                System.out.printf("\t%-30s", k.getWordTagArray()[i].getWordTag());
//                System.out.print("\t\t" + (k.getWordTagArray()[i].getLemma())
//                        + "\t\t" + k.getWordTagArray()[i].getLemmaTag());
//                System.out.println();
//            }
//        });
    }
}
