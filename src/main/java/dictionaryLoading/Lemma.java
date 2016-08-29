package dictionaryLoading;

import java.io.Serializable;

public class Lemma implements Serializable {
    private String lemma, partOfSpeech, tag;

    Lemma(String lemma, String partOfSpeech, String tag) {
        this.lemma = lemma;
        this.partOfSpeech = partOfSpeech;
        this.tag = tag;
    }

    public String getLemma() {
        return lemma;
    }
    public String getTag() {
        return tag;
    }
    public String getPartOfSpeech() {
        return partOfSpeech;
    }
}