package dictionaryLoading;


public class IdiomProperty {
    private String idiom;
    private int lemmaId;
    private String partOfSpeech, tag;

    IdiomProperty(String idiom, int lemmaId, String partOfSpeech, String tag) {
        this.idiom = idiom;
        this.lemmaId = lemmaId;
        this.partOfSpeech = partOfSpeech;
        this.tag = tag;
    }

    public String getIdiom() {
        return idiom;
    }
    public int getLemmaId() {
        return lemmaId;
    }
    public String getPartOfSpeech() {
        return partOfSpeech;
    }
    public String getTag() {
        return tag;
    }
}
