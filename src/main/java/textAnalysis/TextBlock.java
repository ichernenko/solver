package textAnalysis;

import java.io.Serializable;
import java.util.List;

public class TextBlock implements Serializable {
    private int textBlockType;
    private List<Lexeme> lexemes;
    private List<Punctuation> punctuations;

    TextBlock(int textBlockType, List<Lexeme> lexemes, List<Punctuation> punctuations) {
        this.textBlockType = textBlockType;
        this.lexemes = lexemes;
        this.punctuations = punctuations;
    }

    public String getTextBlock() {
        StringBuilder sb = new StringBuilder();
        int lexemesSize = lexemes.size();
        int punctuationSize = punctuations.size();
        int punctuationIndex = 0;

        if (0 < punctuationSize && punctuations.get(0).getOrder() == -1) {
            sb.append(punctuations.get(0).getPunctuation());
            punctuationIndex ++;
        }

        for (int i = 0; i < lexemesSize; i ++) {
            sb.append(lexemes.get(i).getLexeme());
            if (punctuationIndex < punctuationSize && punctuations.get(punctuationIndex).getOrder() == i) {
                sb.append(punctuations.get(punctuationIndex).getPunctuation());
                punctuationIndex ++;
            } else {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    String getTextBlockWithSpaces() {
        StringBuilder sb = new StringBuilder();
        int lexemesSize = lexemes.size();
        int punctuationSize = punctuations.size();
        int punctuationIndex = 0;

        if (0 < punctuationSize && punctuations.get(0).getOrder() == -1) {
            sb.append(punctuations.get(0).getPunctuation());
            punctuationIndex ++;
        }

        for (int i = 0; i < lexemesSize; i ++) {
            sb.append(lexemes.get(i).getLexeme());
            sb.append("(");
            sb.append(lexemes.get(i).getLexemeDescriptor().getAllProperties());
            sb.append(")");

            if (punctuationIndex < punctuationSize && punctuations.get(punctuationIndex).getOrder() == i) {
                char ch = 0;
                char oldCh = 0;
                String punctuation = punctuations.get(punctuationIndex).getPunctuation();
                for (int j = 0; j < punctuation.length(); j++) {
                    ch = punctuation.charAt(j);
                    if (ch == '.' || ch == '!' || ch == '?' || ch == '…' || ch == '¡' || ch == ',' || ch == ';' || ch == ':' || ch == ')' || ch == '}' || ch == ']' || ch == '»') {
                        sb.append(ch);
                    } else {
                        if (ch == '(' || ch == '{' || ch == '[' || ch == '«') {
                            if (oldCh != '(' && oldCh != '{' && oldCh != '[' && oldCh != '«' && oldCh != '—') {
                                sb.append(' ');
                            }
                            sb.append(ch);
                        } else {
                            if (ch == '—') {
                                sb.append(' ');
                                sb.append(ch);
                                sb.append(' ');
                            } else {
                                sb.append(ch);
                            }
                        }
                    }
                    oldCh = ch;
                }
                if (ch == ',' || ch == ';' || ch == ':' || ch == ')' || ch == '}' || ch == ']' || ch == '»') {
                    sb.append(' ');
                }
                punctuationIndex ++;
            } else {
                sb.append(' ');
            }
            sb.append("<br/>");
        }
        return sb.toString();
    }

    public List<Lexeme> getLexemes() {
        return lexemes;
    }
    public int getTextBlockType() {
        return textBlockType;
    }
    public List<Punctuation> getPunctuations() {
        return punctuations;
    }
}
