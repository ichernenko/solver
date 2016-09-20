package textAnalysis;

import java.io.Serializable;
import java.util.List;

public class Paragraph implements Serializable {
    private List<Lexeme> lexemes;

    Paragraph(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public String getParagraph() {
        StringBuilder sb = new StringBuilder(lexemes.get(0).getPunctuations());
        for (int i = 1; i < lexemes.size(); i ++) {
            sb.append(lexemes.get(i).getLexeme());
            if (lexemes.get(i).getPunctuations().length() != 0) {
                sb.append(lexemes.get(i).getPunctuations());
            } else {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    String getParagraphWithSpaces() {
        // Получение всех символов пунктуации, которые расположены до первого символа в параграфе
        StringBuilder sb = new StringBuilder(lexemes.get(0).getPunctuations());

        for (int i = 1; i < lexemes.size(); i ++) {
            sb.append(lexemes.get(i).getLexeme());
            sb.append("(");
            sb.append(lexemes.get(i).getLexemeDescriptor().getAllProperties());
            sb.append(")");
            if (lexemes.get(i).getPunctuations().length() != 0) {
                char ch = 0;
                char oldCh = 0;
                for (int j = 0; j < lexemes.get(i).getPunctuations().length(); j++) {
                    ch = lexemes.get(i).getPunctuations().charAt(j);
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
}
