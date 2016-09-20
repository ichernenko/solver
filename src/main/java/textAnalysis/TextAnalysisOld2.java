package textAnalysis;

import java.util.ArrayList;
import java.util.List;

public class TextAnalysisOld2 {

    // Метод разбивает текст на параграфы и возвращает список этих параграфов с характеристиками параграфов.
    // Параграфом является список лексем, оканчивающийся переходом на новую строку ('\n')
    // Списки параграфов состоят из списков лексем. Лексемы состоят из названия лексемы и списка знаков пунктуации расположенных слева.
    // Предложения на этом этапе выявить еще нельзя!!! Т.к. нельзя однозначно сказать о словах, заканчивающихся на точку, что эта точка
    // завершает предложение, а не является сокращением какого-либо слова. Например, Московская обл.
    public static List<Paragraph> getParagraphs(String text) {
        List<Paragraph> paragraphs = new ArrayList<>();

        if (text != null && text.length() > 0) {
            List<Lexeme> lexemes = new ArrayList<>();
            StringBuilder lexeme = new StringBuilder();
            StringBuilder punctuations = new StringBuilder();
            LexemeDescriptor lexemeDescriptor = null;

            int i = 0;
            char ch;
            while (i < text.length()) {
                // Собираются все символы пунктуации, которые расположены до первой лексемы и сохраняются в 0-м элементе lexemes
                punctuations.setLength(0);
                while (isPunctuation(ch = text.charAt(i)) && i < text.length()) {
                    punctuations.append(ch);
                    i++;
                }
                lexemes.add(new Lexeme(null, punctuations.toString(), null));

                // Проход по всем параграфам
                while (text.charAt(i) != '\n' && i < text.length()) {
                    lexeme.setLength(0);
                    punctuations.setLength(0);
                    lexemeDescriptor = new LexemeDescriptor();
                    // Проход по всем лексемам
                    while (text.charAt(i) != ' ' && !isPunctuation(text.charAt(i)) && text.charAt(i) != '\n' && i < text.length()) {
                        lexeme.append(lexemeDescriptor.analyze(text.charAt(i)));
                        i++;
                    }

                    if (text.charAt(i) == ' ') {
                        i++;
                    } else {
                        while (isPunctuation(text.charAt(i)) && i < text.length()) {
                            punctuations.append(text.charAt(i));
                            i++;
                        }
                    }
                    lexemes.add(new Lexeme(lexeme.toString(), punctuations.toString(), lexemeDescriptor));
                }

                if (text.charAt(i) == '\n') {
                    lexemes.add(new Lexeme(lexeme.toString(), punctuations.toString(), lexemeDescriptor));
                    paragraphs.add(new Paragraph(lexemes));
                    lexemes = new ArrayList<>();
                    i++;
                }
            }
        }
        return paragraphs;
    }

    private static boolean isPunctuation(char ch) {
        return ch == ',' || ch == '.' || ch == '!' || ch == '?' || ch == ';' || ch == ':' ||
                ch == '(' || ch == '{' || ch == '[' || ch == ')' || ch == '}' || ch == ']' || ch == '—' ||
                ch == '«' || ch == '»' || ch == '…' || ch == '¡' ||
                ch == '/' || ch == '\\' || ch == '+' || ch == '-' || ch == '*' || ch == '<' || ch == '>' || ch == '=' ||
                ch == '@' || ch == '＆';
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
