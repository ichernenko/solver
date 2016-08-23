package morphologicAnalysis;

import java.io.Serializable;

public class WordTag implements Serializable{
    private String wordTag, lemma, lemmaTag;

    WordTag(String wordTag, String lemma, String lemmaTag) {
        this.wordTag = wordTag;
        this.lemma = lemma;
        this.lemmaTag = lemmaTag;
    }

    public String getWordTag() {
        return wordTag;
    }
    public String getLemma() {
        return lemma;
    }
    public String getLemmaTag() {
        return lemmaTag;
    }
}
