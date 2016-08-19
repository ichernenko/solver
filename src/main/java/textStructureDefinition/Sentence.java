package textStructureDefinition;

import java.io.Serializable;
import java.util.List;

public class Sentence implements Serializable {
    private char sentenceType;
    private char sentenceEnd;
    //TODO: удалить! необходимо только для отображения!
    private String sentence;
    private List<Word> words;

    Sentence(String sentence, List<Word> words, char sentenceEnd) {
        this.sentence = sentence;
        this.words = words;
        this.sentenceEnd = sentenceEnd;
    }

    public List<Word> getWords() {
        return words;
    }
    public char getSentenceEnd() {
        return sentenceEnd;
    }
    public String getSentence() {
        return sentence;
    }
}
