package morphologicAnalysis;

import java.io.Serializable;
import java.util.List;

public class InnerTextBlock implements Serializable{
    private List<Word> words;
    private char type;

    InnerTextBlock(List<Word> words, char type) {
        this.words = words;
        this.type = type;
    }

    public List<Word> getWords() {
        return words;
    }
    public char getType() {
        return type;
    }
}
