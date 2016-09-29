package morphologicalAnalysis;

import textAnalysis.Punctuation;

import java.io.Serializable;
import java.util.List;

public class Paragraph implements Serializable {
    private List<Word> words;
    private List<Punctuation> punctuations;

    Paragraph(List<Word> words, List<Punctuation> punctuations) {
        this.words = words;
        this.punctuations = punctuations;
    }

    public String getParagraph() {
        StringBuilder sb = new StringBuilder();
        int wordsSize = words.size();
        int punctuationSize = punctuations.size();
        int punctuationIndex = 0;

        if (0 < punctuationSize && punctuations.get(0).getOrder() == -1) {
            sb.append(punctuations.get(0).getPunctuation());
            punctuationIndex ++;
        }

        for (int i = 0; i < wordsSize; i ++) {
            sb.append(words.get(i).getWord());
            if (punctuationIndex < punctuationSize && punctuations.get(punctuationIndex).getOrder() == i) {
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
}
