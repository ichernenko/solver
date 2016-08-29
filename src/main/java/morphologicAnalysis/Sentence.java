package morphologicAnalysis;

import java.io.Serializable;
import java.util.List;

public class Sentence implements Serializable {
    private List<Word> words;
    private List<Punctuation> punctuationMarks;

    Sentence(List<Word> words, List<Punctuation> punctuationMarks) {
        this.words = words;
        this.punctuationMarks = punctuationMarks;
    }

    public String printSentence() {
        StringBuffer sb = new StringBuffer();
        int punctuationIndex = 0;
        for (int i = 0; i < words.size(); i ++) {
            sb.append(words.get(i).getWord());
            if (punctuationMarks.get(punctuationIndex).getWordNumber() == i) {
                sb.append(punctuationMarks.get(punctuationIndex).getPunctuationMark());
                punctuationIndex ++;
            } else {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public String printSentenceWithSpaces() {
        StringBuffer sb = new StringBuffer();
        int punctuationIndex = 0;
        for (int i = 0; i < words.size(); i ++) {
            sb.append(words.get(i).getWord());
            if (punctuationMarks.get(punctuationIndex).getWordNumber() == i) {
                char ch = punctuationMarks.get(punctuationIndex).getPunctuationMark();
                if (ch == '.' || ch == '!' || ch == '?' || ch == '…' || ch == '¡') {
                    sb.append(ch);
                } else {
                    if (ch == ',' || ch == ';' || ch == ':' || ch == ')' || ch == '}' || ch == ']' || ch == '»') {
                        sb.append(ch);
                        sb.append(' ');
                    } else {
                        if (ch == '(' || ch == '{' || ch == '[' || ch == '«') {
                            sb.append(' ');
                            sb.append(ch);
                        } else {
                            if (ch == '—') {
                                sb.append(' ');
                                sb.append(ch);
                                sb.append(' ');
                            }
                        }
                    }
                }
                punctuationIndex ++;
            } else {
                sb.append(' ');
            }
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
