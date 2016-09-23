package textAnalysis;

import java.io.Serializable;

public class Punctuation implements Serializable{
    private String punctuation;
    private int order;

    public Punctuation(String punctuation, int order) {
        this.punctuation = punctuation;
        this.order = order;
    }

    public String getPunctuation() {
        return punctuation;
    }
    public int getOrder() {
        return order;
    }
}
