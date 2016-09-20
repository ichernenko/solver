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
            LexemeDescriptor lexemeDescriptor = null;

            int i = 0;
            boolean isNewParagraph = true;
            boolean isNewLexeme = true;
            char ch;





            while (i < text.length()) {
                // Если параграф новый, то добавляются все символы пунктуации в качестве 0-го элемента lexemes, которые расположены до первого символа в параграфе
                if (isNewParagraph) {
                    lexemes = new ArrayList<>();
                    lexeme.setLength(0);
                    punctuations.setLength(0);
                    lexemeDescriptor = new LexemeDescriptor();

                    while (i < text.length() && isPunctuation(ch = text.charAt(i))) {
                        punctuations.append(ch);
                        i++;
                    }

                    lexemes.add(new Lexeme(null, punctuations.toString(), null));
                    isNewParagraph = false;
                }

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
                                punctuations.setLength(0);

                                i++;
                                while (i < text.length() && isPunctuation(ch = text.charAt(i))) {
                                    punctuations.append(ch);
                                    i++;
                                }

                                lexemes.add(new Lexeme(null, punctuations.toString(), null));
                                isNewLexeme = true;
                                isNewParagraph = true;
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
