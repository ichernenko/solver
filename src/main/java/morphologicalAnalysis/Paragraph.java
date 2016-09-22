package morphologicalAnalysis;

import java.io.Serializable;
import java.util.List;

public class Paragraph implements Serializable {
    private List<Word> words;

    Paragraph(List<Word> words) {
        this.words = words;
    }

    public String getParagraph() {
        return null;
    }

    public List<Word> getWords() {
        return words;
    }
}
