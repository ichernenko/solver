package dictionary;

import java.io.Serializable;

public class Word implements Serializable{
    private String word;
    private WordTag[] wordTagArray;

    Word(String word, WordTag[] wordTagArray) {
        this.word = word;
        this.wordTagArray = wordTagArray;
    }

    public String getWord() {
        return word;
    }
    public WordTag[] getWordTagArray() {
        return wordTagArray;
    }
}