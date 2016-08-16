package textStructureDefinition;


public class SentenceProperty {
    private char sentenceType;
    private char sentenceEnd;
    private String sentence;

    public SentenceProperty(String sentence, char sentenceEnd) {
        this.sentence = sentence;
        this.sentenceEnd = sentenceEnd;
    }

    public String getSentence() {
        return sentence;
    }
    public char getSentenceEnd() {
        return sentenceEnd;
    }
}
