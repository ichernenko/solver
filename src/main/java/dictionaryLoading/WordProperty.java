package dictionaryLoading;

public class WordProperty {
    private int lemmaId;
    private String partOfSpeech, tag;

    WordProperty(int lemmaId, String partOfSpeech, String tag) {
        this.lemmaId = lemmaId;
        this.partOfSpeech = partOfSpeech;
        this.tag = tag;
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

