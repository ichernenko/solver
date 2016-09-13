package textAnalysis;

import preliminaryTextProcessing.PreliminaryTextProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextAnalysis {

    // Метод разбивает текст на параграфы и возвращает список этих параграфов с характеристиками параграфов.
    // Параграфом является список лексем, оканчивающийся переходом на новую строку ('\n')
    // Списки параграфов состоят из списков слов и списков пунктуаций.
    // Предложения на этом этапе выявить еще нельзя!!! Т.к. нельзя однозначно сказать о словах, заканчивающихся на точку, что эта точка
    // завершает предложение, а не является сокращением какого-либо слова. Например, Московская обл.
    public static List<Paragraph> getParagraphs(String text) {
        List<Paragraph> paragraphs = new ArrayList<>();

        if (text != null && text.length() > 0) {
            List<Word> words = new ArrayList<>();
            List<Punctuation> punctuationMarks = new ArrayList<>();
            StringBuilder word = new StringBuilder();

            int wordNumber = -1; // До первого слова
            boolean isParagraph = false;
            boolean isWord = false;
            WordDescriptor wordDescriptor = new WordDescriptor();

            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                if (ch == '\n') {
                    if(isWord) {
                        words.add(new Word(word.toString(), wordDescriptor));
                        isWord = false;
                    }
                    if(isParagraph) {
                        paragraphs.add(new Paragraph(words, punctuationMarks));
                        words = new ArrayList<>();
                        punctuationMarks = new ArrayList<>();
                        word.setLength(0);
                        wordNumber = -1;
                        isParagraph = false;
                    }
                } else {
                    if (ch == ',' || ch == '.' || ch == '!' || ch == '?' || ch == ';' || ch == ':' ||
                        ch == '(' || ch == '{' || ch == '[' || ch == ')' || ch == '}' || ch == ']' || ch == '—' ||
                        ch == '«' || ch == '»' || ch == '…' || ch == '¡' ||
                        ch == '/' || ch == '\\' || ch == '+' || ch == '-' || ch == '*' || ch == '<' || ch == '>' || ch == '=' ||
                        ch == '@' || ch == '＆') {

                        if(isWord) {
                            words.add(new Word(word.toString(), wordDescriptor));
                            isWord = false;
                        }
                        punctuationMarks.add(new Punctuation(ch, wordNumber));
                    } else {
                        if (ch == ' ') {
                            words.add(new Word(word.toString(), wordDescriptor));
                            isWord = false;
                        } else {
                            if (!isParagraph) {
                                wordNumber = 0;
                                isWord = true;
                                isParagraph = true;
                            } else {
                                if (!isWord) {
                                    word.setLength(0);
                                    wordDescriptor = new WordDescriptor();
                                    wordNumber ++;
                                    isWord = true;
                                }
                            }
                            word.append(wordDescriptor.analyze(ch));
                        }
                    }
                }
            }
        }
        return paragraphs;
    }


    private void findFullName() {
        String text = "И.А.Черненко-Ива Обычно,И. А.Хру ,все хорошо но не так как. Хру-Черненко И. А.  ";
        text = PreliminaryTextProcessing.getResult(text);
        System.out.println(text);
        Pattern p1 = Pattern.compile("[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)*? [А-ЯЁ]\\.[А-ЯЁ]\\.|[А-ЯЁ]\\.[А-ЯЁ]\\.[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)*");
        Matcher m = p1.matcher (text);
        while (m.find()) {
            System.out.println(m.group(0));
        }

    }

    // Метод возвращает строку, состоящую из отформатированных параграфов
    public static String getResult(List<Paragraph> paragraphs) {
        StringBuilder sb = new StringBuilder();
        paragraphs.forEach(m -> {
            sb.append(m.getParagraphWithSpaces());
            sb.append("<br/>");
        });
        return sb.toString();
    }
}
