package textStructureDefinition;

import java.io.Serializable;
import java.util.List;

public class Sentence implements Serializable {
    private char sentenceType;
    private char sentenceEnd;
    //TODO: удалить! необходимо только для отображения!
    private String sentence;
    private List<Word> wordList;

    Sentence(String sentence, List<Word> wordList, char sentenceEnd) {
        this.sentence = sentence;
        this.wordList = wordList;
        this.sentenceEnd = sentenceEnd;
    }

    public List<Word> getWordList() {
        return wordList;
    }
    public char getSentenceEnd() {
        return sentenceEnd;
    }
    public String getSentence() {
        return sentence;
    }
}
