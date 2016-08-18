import textStructureDefinition.Sentence;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String... args) {
//        String text = "  Пусть как   говориться  ,,  обычное !!!!! предложение..... (h )  100    0  000,а.   ) и !!?!    ?!??!все тут!   \n";
        String text = "     Пусть бегут 1 1 1 3 ' ( ?   )  . .  И на улице (  привет  }  у нас.И в лесу растет ... Класс  ? ";

//        String text = "  Пусть как   говориться  ..   ";
        List<Sentence> sentencePropertyList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        boolean wasSpace = false;
        boolean wasDigitOrPunctuation2 = false;
        char lastAppendedCh = 0;

        text = text.replaceAll("^\\s+|\\s+$","")
                .replaceAll("\\s+"," ")
                .replaceAll(" (,|;|\\.|!|\\?|:|\\)|\\}|\\]|%)","$1")
                .replaceAll("(\\(|\\{|\\[|№) ","$1")
                .replaceAll("\\.{3,}","…")
                .replaceAll("(\\.|,|;|!|\\?|:)\\1+","$1")
                .replaceAll("(!|\\?){2,}","¡")
                .replaceAll(" \\((\\?|\\!)\\)","")
                // удаление пробелов в числах
                .replaceAll("([0-9]) ([0-9])*?","$1$2")
                // pL - буквы русского алфавита
                .replaceAll("(,|;|\\.|!|\\?|:|\\)|\\}|\\]|¡)([0-9]|\\pL)", "$1 $2");

        System.out.println("|" + text + "|\n");


//        for (int i = 0; i < text.length(); i++) {
//            char ch = text.charAt(i);
//            // удаление непечатаемых символьных последовательностей
//            if (!isNonPrintableCharacter(ch)) {
//                // вставка символа пробела после символа пунктуации, если его там не было
//                if (isPunctuation1(lastAppendedCh) && !isPunctuation1(ch) && !wasSpace) {
//                    sb.append(' ');
//                    wasSpace = false;
//                } else {
//                    // удаление непечатаемых символов в числах и после некоторых символов пунктуации
//                    if (wasDigitOrPunctuation2 && !Character.isDigit(ch) && !isPunctuation1(ch)) {
//                        sb.append(' ');
//                        wasSpace = false;
//                        wasDigitOrPunctuation2 = false;
//                    }
//                }
//
//                if (wasSpace) {
//                    if (!isPunctuation1(ch)) {
//                        sb.append(' ');
//                    }
//                    wasSpace = false;
//                }
//
//                sb.append(ch);
//                lastAppendedCh = ch;
//            } else {
//                if (!Character.isDigit(lastAppendedCh) && !isPunctuation2(lastAppendedCh)) {
//                    wasSpace = true;
//                } else {
//                    wasDigitOrPunctuation2 = true;
//                }
//            }
//        }

//        System.out.println("|" + sb + "|\n");

//        text = sb.toString().replaceAll("\\.{3,}","…").replaceAll("\\!{2,}","!").replaceAll("(\\!|\\?){2,}","¡").replaceAll("\\((\\?|\\!)\\) ","");

//        // разбиение текста на предложения
//        int sentenceBegin = 0;
//        for (int i = 0; i < text.length(); i++) {
//            char ch = text.charAt(i);
//            switch(ch) {
//                case '.' : ;
//                case '!' : ;
//                case '?' : ;
//                case '…':
//                    sentenceList.add(new Sentence(getWordList(text.substring(sentenceBegin, i)), ch));
//                        sentenceBegin = i + 2; // + 2 это пропуск пробела после предложения
//            }
//        }
//
//        sentencePropertyList.forEach(k -> System.out.println(k.getSentence() + k.getSentenceEnd()));

    }

    private static boolean isNonPrintableCharacter(char ch) {
        return ch == ' ' || ch == '\t' || ch == '\f' || ch == '\n' || ch == '\r';
    }

    private static boolean isPunctuation1(char ch) {
        return ch == ',' || ch == '.' || ch == '!' || ch == '?' || ch == ';' || ch == '"' || ch == '\'' || ch == ':' || ch == ')' || ch == '}' || ch == ']';
    }

    private static boolean isPunctuation2(char ch) {
        return ch == '"' || ch == '\'' || ch == '(' || ch == '{' || ch == '[';
    }
}
