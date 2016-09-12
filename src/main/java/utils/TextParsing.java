package utils;

import preliminaryTextProcessing.PreliminaryTextProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ichernenko on 12.09.2016.
 */
public class TextParsing {
    private static Pattern
            // поиск фамилий и инициала(ов) в начале или конце фамилии
            p1 = Pattern.compile("[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)*? [А-ЯЁ]\\.([А-ЯЁ]\\.)*|[А-ЯЁ]\\.([А-ЯЁ]\\.)*[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)*"),
    // поиск общепринятых сокращений с точкой на конце
    p2 = Pattern.compile("([а-яё]+\\.)+");

    public static void main(String[] args) {
//        String text = "  Пусть как   говориться  ,,  обычное !!!!! предложение..... (h )  100    0  000,а.   ) и !!?!    ?!??!все тут";
//        String text = "   №1 $ 2 3 ;  № 1 Пусть 54% 6 7    °   3 c “(  rrr ’ бегут 1 1 1 3 ' ( ?   )  . .  И на улице (  привет  }  у нас.И в лесу растет ... Класс  ? ";
        String text = "Че И.А.че Обычно,Хру А. у,все хорошо вкт.но не так к. Черненко р.о.п., кап.нал.мил.";
//        String text = "\"Мальчик\", -\"курит\" -[{  } папиросу    }] . \"Остальные\" , ром";
//        String text = "Все. ок!";

        text = PreliminaryTextProcessing.getResult(text);
        System.out.println("_123456789_123456789_123456789_123456789_123456789_123456789_123456789");
        System.out.println(text);


        List<Integer> parts = new ArrayList<>();
        // Четный индекс - start, нечетный - end
        parts.add(0);
//        parts.add(6);
//        parts.add(35);
        parts.add(text.length());
        parts = findLexeme(text, parts, p1, 4, TextParsing::checkForFullName);
        print(text, parts);
        parts = findLexeme(text, parts, p2, 2, TextParsing::checkForCommonAbbreviation);
        print(text, parts);
    }

    private static List<Integer> findLexeme(String text, List<Integer> oldParts, Pattern p, int minPartLength, SentenceEndMethod<String, Integer> sentenceEndMethod) {
        List<Integer> newParts = new ArrayList<>();
        Matcher m = p.matcher(text);
        for (int i = 0; i < oldParts.size(); i += 2) {
            int start = oldParts.get(i);
            int end = oldParts.get(i + 1);

            if (end - start < minPartLength) {  // Если количество символов в части меньше minPartLength, то анализ такой части не производится
                newParts.add(start);
                newParts.add(end);
                continue;
            }

            m = m.region(start, end);
            int startIndex = newParts.size();
            boolean isFound = false;

            while (m.find()) {
                System.out.println(m.group() + "  " + m.start() + "  " + m.end());
                newParts.add(m.start());
                // Если последний символ - точка, а следующий - заглавная буква или конец текста,
                // то точка после инициалов является знаком завершающим предложение.
                // Фамилия и инициалы добавляются как экземпляр класса FullName.
                // Последняя точка остается для последующего разбора текста на блоки и предложения
                if (sentenceEndMethod.check(text, m.end())) {
                    newParts.add(m.end() - 1);
                } else {
                    newParts.add(m.end());
                }
                isFound = true;

            }

            if (isFound) {
                if (newParts.get(startIndex) == start) {
                    newParts.remove(startIndex);
                } else {
                    newParts.add(startIndex, start);
                }
                if (newParts.get(newParts.size() - 1) == end) {
                    newParts.remove(newParts.size() - 1);
                } else {
                    newParts.add(newParts.size(), end);
                }
            } else {
                newParts.add(start);
                newParts.add(end);
            }
        }
        return newParts;
    }

    private static boolean checkForFullName(String text, Integer end) {
        return text.charAt(end - 1) == '.' && ((end < text.length() && Character.isLetter(text.charAt(end)) && text.charAt(end) == Character.toUpperCase(text.charAt(end))) || end == text.length());
    }

    // Необходимо добавить проверку на правая - левая позиция для аббревиатуры. Если левая в БД, то можно не проверять на Заглавную букву, если правая - стандартный механизм.
    private static boolean checkForCommonAbbreviation(String text, Integer end) {
        return text.charAt(end - 1) == '.' && ((end < text.length() && Character.isLetter(text.charAt(end)) && text.charAt(end) == Character.toUpperCase(text.charAt(end))) || end == text.length());
    }

    private static void print(String text, List<Integer> parts) {
        for (int i = 0; i < parts.size(); i += 2) {
            System.out.println(text.substring(parts.get(i), parts.get(i + 1)));
        }

        System.out.println(parts);
    }



    @FunctionalInterface
    private interface SentenceEndMethod<F, T> {
        boolean check(F f, T t);
    }
}