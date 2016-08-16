package wordDictionary;

public class Lemma {
    private String lemma, tag;

    Lemma(String lemma, String tag) {
        this.lemma = lemma;
        this.tag = tag;
    }

    public String getLemma() {
        return lemma;
    }
    public String getTag() {
        return tag;
    }
}