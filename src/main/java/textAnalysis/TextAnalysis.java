package textAnalysis;

import java.util.ArrayList;
import java.util.List;

public class TextAnalysis {

    // Метод разбивает текст на текстовые блоки и возвращает список этих текстовых блоков.
    // Текстовые блоки состоят из списков лексем, списка пунктуации, типа текстового блока.
    // Тип 1 - главный блок - список лексем, которые оканчиваются переходом на новую строку ('\n').
    // Лексема состоит из названия и определяющего набора характеристик.
    // Предложения на этом этапе выявить еще нельзя!!! Т.к. нельзя однозначно сказать о словах, заканчивающихся на точку, что эта точка
    // завершает предложение, а не является сокращением какого-либо слова. Например, Московская обл.
    public static List<TextBlock> getTextBlocks(String text) {
        List<TextBlock> textBlocks = new ArrayList<>();

        if (text != null && text.length() > 0) {
            List<Lexeme> lexemes = new ArrayList<>();
            StringBuilder lexeme = new StringBuilder();
            StringBuilder punctuation = new StringBuilder();
            List<Punctuation> punctuations = new ArrayList<>();
            LexemeDescriptor lexemeDescriptor = new LexemeDescriptor();

            int i = 0;
            int lexemeOrder = 0;
            boolean isNewLexeme = false;
            char ch;

            while (i < text.length() && isPunctuation(ch = text.charAt(i))) {
                punctuation.append(ch);
                i++;
            }

            if (punctuation.length() > 0) {
                punctuations.add(new Punctuation(punctuation.toString(), -1));
                punctuation.setLength(0);
            }

            while (i < text.length()) {
                ch = text.charAt(i);
                if (ch == ' ') {
                    isNewLexeme = true;
                } else {
                    if (isPunctuation(ch)) {
                        punctuation.append(ch);
                        isNewLexeme = true;
                    } else {
                        if (ch == '\n') {
                            lexemes.add(new Lexeme(lexeme.toString(), lexemeDescriptor));
                            punctuations.add(new Punctuation(punctuation.toString(), lexemeOrder));
                            textBlocks.add(new TextBlock(1, lexemes, punctuations));

                            // Подготовка к новому текстовому блоку (сбор всех знаков пунктуации до первой лексемы)
                            if (i < text.length() - 1) {
                                lexemes = new ArrayList<>();
                                lexeme.setLength(0);
                                punctuation.setLength(0);
                                punctuations = new ArrayList<>();
                                lexemeOrder = 0;
                                lexemeDescriptor = new LexemeDescriptor();

                                while (i < text.length() - 1 && isPunctuation(ch = text.charAt(i + 1))) {
                                    punctuation.append(ch);
                                    i++;
                                }

                                if (punctuation.length() > 0) {
                                    punctuations.add(new Punctuation(punctuation.toString(), -1));
                                    punctuation.setLength(0);
                                }
                                isNewLexeme = false;
                            }
                        } else {
                            if (isNewLexeme == true) {
                                lexemes.add(new Lexeme(lexeme.toString(), lexemeDescriptor));
                                lexeme.setLength(0);
                                if (punctuation.length() > 0) {
                                    punctuations.add(new Punctuation(punctuation.toString(), lexemeOrder));
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
