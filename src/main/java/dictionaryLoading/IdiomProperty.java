package dictionaryLoading;


public class IdiomProperty {
    private String idiomTail;
    private int idiomTailLexemesNumber;
    private int lemmaId;
    private String partOfSpeech, tag;

    IdiomProperty(String idiomTail, int idiomTailLexemesNumber, int lemmaId, String partOfSpeech, String tag ) {
        this.idiomTail = idiomTail;
        this.idiomTailLexemesNumber = idiomTailLexemesNumber;
        this.lemmaId = lemmaId;
        this.partOfSpeech = partOfSpeech;
        this.tag = tag;
    }

    public String getIdiomTail() {
        return idiomTail;
    }
    public int getIdiomTailLexemesNumber() {
        return idiomTailLexemesNumber;
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
