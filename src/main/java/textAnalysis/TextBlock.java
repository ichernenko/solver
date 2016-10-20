package textAnalysis;

import common.Range;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TextBlock implements Serializable {
    private int textBlockType;
    private List<Lexeme> lexemes;
    private String textBlockPunctuation;
    private List<Range> unprocessedRanges;

    TextBlock(int textBlockType, List<Lexeme> lexemes, String textBlockPunctuation) {
        this.textBlockType = textBlockType;
        this.lexemes = lexemes;
        this.textBlockPunctuation = textBlockPunctuation;
        this.unprocessedRanges = new LinkedList<>();
        unprocessedRanges.add(new Range(0, lexemes.size()));
    }

    public String getTextBlock() {
        StringBuilder sb = new StringBuilder(textBlockPunctuation);
        for (Lexeme lexeme : lexemes) {
            sb.append(lexeme.getLexeme());
            String punctuation = lexeme.getPunctuation();
            sb.append(punctuation == null ?  " " : punctuation);
        }
        return sb.toString();
    }

    String getTextBlockWithSpaces() {
        StringBuilder sb = new StringBuilder(textBlockPunctuation);

        for (Lexeme lexeme: lexemes) {
            sb.append(lexeme.getLexeme());
            sb.append("(");
            sb.append(lexeme.getLexemeDescriptor().getAllProperties());
            sb.append(")");

            String punctuation = lexeme.getPunctuation();
            if (!punctuation.isEmpty()) {
                char ch = 0;
                char oldCh = 0;
                int punctuationLength = punctuation.length();
                for (int j = 0; j < punctuationLength; j++) {
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
    public String getTextBlockPunctuation() {
        return textBlockPunctuation;
    }
    public List<Range> getUnprocessedRanges() {
        return unprocessedRanges;
    }
}
