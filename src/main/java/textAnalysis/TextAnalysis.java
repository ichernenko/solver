package textAnalysis;

import java.util.ArrayList;
import java.util.List;

public class TextAnalysis {

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
            LexemeDescriptor lexemeDescriptor = new LexemeDescriptor();

            int i = 0;
            boolean isNewLexeme = false;
            char ch;

            while (i < text.length() && isPunctuation(ch = text.charAt(i))) {
                punctuations.append(ch);
                i++;
            }

            lexemes.add(new Lexeme(null, punctuations.toString(), null));
            punctuations.setLength(0);

            while (i < text.length()) {
                ch = text.charAt(i);
                if (ch == ' ') {
                    isNewLexeme = true;
                } else {
                    if (isPunctuation(ch)) {
                        punctuations.append(ch);
                        isNewLexeme = true;
                    } else {
                        if (ch == '\n') {
                            lexemes.add(new Lexeme(lexeme.toString(), punctuations.toString(), lexemeDescriptor));
                            paragraphs.add(new Paragraph(lexemes));

                            // Подготовка к новому параграфу (сбор всех знаков пунктуации до первой лексемы)
                            if (i < text.length() - 1) {
                                lexemes = new ArrayList<>();
                                lexeme.setLength(0);
                                punctuations.setLength(0);
                                lexemeDescriptor = new LexemeDescriptor();

                                while (i < text.length() - 1 && isPunctuation(ch = text.charAt(i + 1))) {
                                    punctuations.append(ch);
                                    i++;
                                }

                                lexemes.add(new Lexeme(null, punctuations.toString(), null));
                                punctuations.setLength(0);
                                isNewLexeme = false;
                            }
                        } else {
                            if (isNewLexeme == true) {
                                lexemes.add(new Lexeme(lexeme.toString(), punctuations.toString(), lexemeDescriptor));
                                lexeme.setLength(0);
                                punctuations.setLength(0);
                                lexemeDescriptor = new LexemeDescriptor();
                                isNewLexeme = false;
                            }
                            lexeme.append(lexemeDescriptor.analyze(ch));
                        }
                    }
                }
                i++;
            }
        }
        return paragraphs;
    }

    private static boolean isPunctuation(char ch) {
        return  ch == ',' || ch == '.' || ch == '!' || ch == '?' || ch == ';' || ch == ':' ||
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
