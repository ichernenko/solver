package textAnalysis;

import java.io.Serializable;

public class Punctuation implements Serializable{
    private String punctuation;
    private int lexemeOrder;

    public Punctuation(String punctuation, int lexemeOrder) {
        this.punctuation = punctuation;
        this.lexemeOrder = lexemeOrder;
    }

    public String getPunctuation() {
        return punctuation;
    }
    public int getLexemeOrder() {
        return lexemeOrder;
    }
}
