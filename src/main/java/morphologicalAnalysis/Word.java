package morphologicalAnalysis;

import java.io.Serializable;

public class Word implements Serializable, Comparable<Word> {
    private int order;
    private String word;
    private WordTag[] wordTags;
    private int lexemesNumber = 1;

    Word(int order, String word, WordTag[] wordTags) {
        this.order = order;
        this.word = word;
        this.wordTags = wordTags;
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
    public int getLexemesNumber() {
        return lexemesNumber;
    }
    public void setLexemesNumber(int lexemesNumber) {
        this.lexemesNumber = lexemesNumber;
    }
}