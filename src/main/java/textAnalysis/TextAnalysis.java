package textAnalysis;

import java.util.ArrayList;
import java.util.List;

public class TextAnalysis {

    // Метод разбивает текст на текстовые блоки и возвращает список этих текстовых блоков.
    // Текстовые блоки состоят из списков лексем, пунктуации до первой лексемы, типа текстового блока.
    // Тип 1 - главный блок - список лексем, которые оканчиваются переходом на новую строку ('\n').
    // Лексема состоит из названия, определяющего набора характеристик и пунктуации, следующей за лексемой.
    // Предложения на этом этапе выявить еще нельзя!!! Т.к. нельзя однозначно сказать о словах, заканчивающихся на точку, что эта точка
    // завершает предложение, а не является сокращением какого-либо слова. Например, Московская обл.
    public static List<TextBlock> getTextBlocks(String text) {
        List<TextBlock> textBlocks = new ArrayList<>();

        if (text != null && text.length() > 0) {
            List<Lexeme> lexemes = new ArrayList<>();
            StringBuilder lexeme = new StringBuilder();
            StringBuilder punctuation = new StringBuilder();
            LexemeDescriptor lexemeDescriptor = new LexemeDescriptor();
            String textBlockPunctuation = "";

            int i = 0;
            int lexemeOrder = 0;
            boolean isNewLexeme = false;
            char ch;
            int textLength = text.length();

            while (i < textLength && isPunctuation(text.charAt(i))) {
                i ++;
            }

            if (i > 0) {
                textBlockPunctuation = text.substring(0, i);
            }

            while (i < textLength) {
                ch = text.charAt(i);
                if (ch == ' ') {
                    isNewLexeme = true;
                } else {
                    if (isPunctuation(ch)) {
                        punctuation.append(ch);
                        isNewLexeme = true;
                    } else {
                        if (ch == '\n') {
                            lexemes.add(new Lexeme(lexemeOrder, lexeme.toString(), lexemeDescriptor, punctuation.toString()));
                            textBlocks.add(new TextBlock(1, lexemes, textBlockPunctuation));

                            // Подготовка к новому текстовому блоку (сбор всех знаков пунктуации до первой лексемы)
                            if (i < textLength - 1) {
                                lexemes = new ArrayList<>();
                                lexeme.setLength(0);
                                punctuation.setLength(0);
                                textBlockPunctuation = "";
                                lexemeOrder = 0;
                                lexemeDescriptor = new LexemeDescriptor();

                                if (i < textLength - 1 && isPunctuation(text.charAt(i + 1))) {
                                    i ++;
                                    while (i < textLength - 1 && isPunctuation(text.charAt(i + 1))) {
                                        i ++;
                                    }
                                    textBlockPunctuation = text.substring(0, i);
                                }
                                isNewLexeme = false;
                            }
                        } else {
                            if (isNewLexeme) {
                                lexemes.add(new Lexeme(lexemeOrder, lexeme.toString(), lexemeDescriptor, punctuation.toString()));
                                lexeme.setLength(0);
                                if (punctuation.length() > 0) {
                                    punctuation.setLength(0);
                                }
                                lexemeDescriptor = new LexemeDescriptor();
                                lexemeOrder++;
                                isNewLexeme = false;
                            }
                            lexeme.append(lexemeDescriptor.analyze(ch));
                        }
                    }
                }
                i++;
            }
        }
        return textBlocks;
    }

    private static boolean isPunctuation(char ch) {
        return ch == ',' || ch == '.' || ch == '!' || ch == '?' || ch == ';' || ch == ':' ||
                ch == '(' || ch == '{' || ch == '[' || ch == ')' || ch == '}' || ch == ']' || ch == '—' ||
                ch == '«' || ch == '»' || ch == '…' || ch == '¡' ||
                ch == '/' || ch == '\\' || ch == '+' || ch == '-' || ch == '*' || ch == '<' || ch == '>' || ch == '=' ||
                ch == '@' || ch == '＆';
    }

    // Метод возвращает строку, состоящую из отформатированных параграфов
    public static String getResult(List<TextBlock> textBlocks) {
        StringBuilder sb = new StringBuilder();
        textBlocks.forEach(textBlock -> {
            sb.append(textBlock.getTextBlockWithSpaces());
            sb.append("<br/>");
        });
        return sb.toString();
    }
}
