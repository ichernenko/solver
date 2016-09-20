package textAnalysis;

import morphologicalAnalysis.WordTag;

import java.io.Serializable;

public class Word implements Serializable{
    private String word;
    private LexemeDescriptor lexemeDescriptor;
    private WordTag[] wordTags;

    Word(String word, LexemeDescriptor lexemeDescriptor) {
        this.word = word;
        this.lexemeDescriptor = lexemeDescriptor;
    }

    public String getWord() {
        return word;
    }
    public LexemeDescriptor getLexemeDescriptor() {
        return lexemeDescriptor;
    }
    public WordTag[] getWordTags() {
        return wordTags;
    }
    public void setWordTags(WordTag[] wordTags) {
        this.wordTags = wordTags;
    }
    public void setWord(String word) {
        this.word = word;
    }
}