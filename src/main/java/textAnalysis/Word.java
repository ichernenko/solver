package textAnalysis;

import morphologicAnalysis.WordTag;

import java.io.Serializable;

public class Word implements Serializable{
    private String word;
    private WordDescriptor wordDescriptor;
    private WordTag[] wordTags;

    Word(String word, WordDescriptor wordDescriptor) {
        this.word = word;
        this.wordDescriptor = wordDescriptor;
    }

    public String getWord() {
        return word;
    }
    public WordDescriptor getWordDescriptor() {
        return wordDescriptor;
    }
    public WordTag[] getWordTags() {
        return wordTags;
    }
    public void setWordTags(WordTag[] wordTags) {
        this.wordTags = wordTags;
    }
}