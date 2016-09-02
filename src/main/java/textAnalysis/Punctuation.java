package textAnalysis;

import java.io.Serializable;

public class Punctuation implements Serializable{
    private char punctuationMark;
    private int wordNumber;

    public Punctuation(char punctuationMark, int wordNumber) {
        this.punctuationMark = punctuationMark;
        this.wordNumber = wordNumber;
    }

    public char getPunctuationMark() {
        return punctuationMark;
    }
    public int getWordNumber() {
        return wordNumber;
    }
}
