package loading.dictionary;

public class Lemma {
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