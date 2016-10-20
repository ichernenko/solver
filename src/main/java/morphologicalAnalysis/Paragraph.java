package morphologicalAnalysis;

import java.io.Serializable;
import java.util.List;

public class Paragraph implements Serializable {
    private List<Word> words;
    private String paragraphPunctuation;

    Paragraph(List<Word> words, String paragraphPunctuation) {
        this.words = words;
        this.paragraphPunctuation = paragraphPunctuation;
    }

    public String getParagraph() {
        StringBuilder sb = new StringBuilder(paragraphPunctuation);
        for (Word word : words) {
            sb.append(word.getWord());
            String punctuation = word.getPunctuation();
            sb.append(punctuation.isEmpty() ?  " " : punctuation);
        }
        return sb.toString();
    }

    public List<Word> getWords() {
        return words;
    }
    public String getParagraphPunctuations() {
        return paragraphPunctuation;
    }
}
