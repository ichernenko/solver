package textAnalysis;

import java.io.Serializable;

public class Punctuation implements Serializable{
    private int order;
    private String punctuation;

    public Punctuation(int order, String punctuation) {
        this.order = order;
        this.punctuation = punctuation;
    }

    public int getOrder() {
        return order;
    }
    public String getPunctuation() {
        return punctuation;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
