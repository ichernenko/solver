package textAnalysis;

import java.io.Serializable;
import java.util.List;

public class Paragraph implements Serializable {
    private List<Word> words;
    private List<Punctuation> punctuationMarks;

    Paragraph(List<Word> words, List<Punctuation> punctuationMarks) {
        this.words = words;
        this.punctuationMarks = punctuationMarks;
    }

    public String getParagraph() {
        StringBuilder sb = new StringBuilder();
        int punctuationIndex = 0;
        // Получение всех символов пунктуации, которые расположены до первого символа в параграфе
        while (punctuationIndex < punctuationMarks.size() && punctuationMarks.get(punctuationIndex).getWordNumber() == -1) {
            sb.append(punctuationMarks.get(punctuationIndex).getPunctuationMark());
            punctuationIndex ++;
        }

        for (int i = 0; i < words.size(); i ++) {
            sb.append(words.get(i).getWord());
            if (punctuationMarks.get(punctuationIndex).getWordNumber() == i) {
                while (punctuationIndex < punctuationMarks.size() && punctuationMarks.get(punctuationIndex).getWordNumber() == i) {
                    sb.append(punctuationMarks.get(punctuationIndex).getPunctuationMark());
                    punctuationIndex++;
                }
            } else {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    String getParagraphWithSpaces() {
        StringBuilder sb = new StringBuilder();
        int punctuationIndex = 0;
        // Получение всех символов пунктуации, которые расположены до первого символа в параграфе
        while (punctuationIndex < punctuationMarks.size() && punctuationMarks.get(punctuationIndex).getWordNumber() == -1) {
            sb.append(punctuationMarks.get(punctuationIndex).getPunctuationMark());
            punctuationIndex ++;
        }

        for (int i = 0; i < words.size(); i ++) {
            sb.append(words.get(i).getWord());
            sb.append("(");
            sb.append(words.get(i).getWordDescriptor().getAllProperties());
            sb.append(")");
            if (punctuationMarks.get(punctuationIndex).getWordNumber() == i) {
                char ch = 0;
                char oldCh = 0;
                while (punctuationIndex < punctuationMarks.size() && punctuationMarks.get(punctuationIndex).getWordNumber() == i) {
                    ch = punctuationMarks.get(punctuationIndex).getPunctuationMark();
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
                    punctuationIndex++;
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

    public List<Word> getWords() {
        return words;
    }
    public List<Punctuation> getPunctuationMarks() {
        return punctuationMarks;
    }
}
