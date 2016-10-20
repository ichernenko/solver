package morphologicalAnalysis;

import common.ElementCountable;

import java.io.Serializable;

public class Word implements ElementCountable, Serializable, Comparable<Word> {
    private int order;
    private String word;
    private WordTag[] wordTags;
    private String punctuation;
    private int elementNumber = 1;

    Word(int order, String word, WordTag[] wordTags, String punctuation) {
        this.order = order;
        this.word = word;
        this.wordTags = wordTags;
        this.punctuation = punctuation;
    }

    @Override
    public int compareTo(Word comparedWord){
        return this.order - comparedWord.order;
    }

    public int getOrder() {
        return order;
    }
    public String getWord() {
        return word;
    }
    public WordTag[] getWordTags() {
        return wordTags;
    }
    public String getPunctuation() {
        return punctuation;
    }

    @Override
    public int getElementNumber() {
        return elementNumber;
    }
    public void setElementNumber(int elementNumber) {
        this.elementNumber = elementNumber;
    }
}