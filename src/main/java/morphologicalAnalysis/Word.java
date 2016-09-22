package morphologicalAnalysis;

import java.io.Serializable;

public class Word implements Serializable, Comparable<Word> {
    private int order;
    private String word;
    private String punctuations;
    private WordTag[] wordTags;

    Word(int order, String word) {
        this.order = order;
        this.word = word;
    }

    @Override
    public int compareTo(Word comparedWord){
        return this.order - comparedWord.order;
    }

    public String getWord() {
        return word;
    }
    public String getPunctuations() {
        return punctuations;
    }
    public WordTag[] getWordTags() {
        return wordTags;
    }
}