package dictionaryLoading;


public class IdiomProperty {
    private String idiomTail;
    private int lemmaId;
    private String partOfSpeech, tag;

    IdiomProperty(String idiomTail, int lemmaId, String partOfSpeech, String tag) {
        this.idiomTail = idiomTail;
        this.lemmaId = lemmaId;
        this.partOfSpeech = partOfSpeech;
        this.tag = tag;
    }

    public String getIdiomTail() {
        return idiomTail;
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
