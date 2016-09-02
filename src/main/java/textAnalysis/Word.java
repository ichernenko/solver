package textAnalysis;

import morphologicAnalysis.WordTag;

import java.io.Serializable;

public class Word implements Serializable{
    private String word;
    private WordTag[] wordTags;

    Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
    public WordTag[] getWordTags() {
        return wordTags;
    }
    public void setWordTags(WordTag[] wordTags) {
        this.wordTags = wordTags;
    }
}