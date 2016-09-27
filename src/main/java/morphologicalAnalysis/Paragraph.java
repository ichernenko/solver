package morphologicalAnalysis;

import textAnalysis.Punctuation;

import java.io.Serializable;
import java.util.List;

public class Paragraph implements Serializable {
    private List<Word> words;
    private List<Punctuation> punctuations;
    private List<Integer> rawWordsRange;

    Paragraph(List<Word> words, List<Punctuation> punctuations, List<Integer> rawWordsRange) {
        this.words = words;
        this.punctuations = punctuations;
        this.rawWordsRange = rawWordsRange;
    }

    public String getParagraph() {
        StringBuilder sb = new StringBuilder();
        if (0 < punctuations.size() && punctuations.get(0).getOrder() == -1) {
            sb.append(punctuations.get(0).getPunctuation());
        }

        int punctuationIndex = 0;
        for (int i = 0; i < words.size(); i ++) {
            sb.append(words.get(i).getWord());
            if (punctuationIndex < punctuations.size() && punctuations.get(punctuationIndex).getOrder() == i) {
                sb.append(punctuations.get(punctuationIndex).getPunctuation());
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
    public List<Punctuation> getPunctuations() {
        return punctuations;
    }
    public List<Integer> getRawWordsRange() {
        return rawWordsRange;
    }
}
