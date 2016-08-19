package textStructureDefinition;

import java.io.Serializable;

public class Word implements Serializable{
    private String word;
    private WordTag[] wordTags;

    Word(String word, WordTag[] wordTags) {
        this.word = word;
        this.wordTags = wordTags;
    }

    public String getWord() {
        return word;
    }
    public WordTag[] getWordTags() {
        return wordTags;
    }
}