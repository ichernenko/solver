package textAnalysis;

import java.io.Serializable;

public class Lexeme implements Serializable {
    private int order;
    private String lexeme;
    private LexemeDescriptor lexemeDescriptor;
    private String punctuation;

    public Lexeme(int order, String lexeme, LexemeDescriptor lexemeDescriptor, String punctuation) {
        this.order = order;
        this.lexeme = lexeme;
        this.lexemeDescriptor = lexemeDescriptor;
        this.punctuation = punctuation;
    }

    public int getOrder() {
        return order;
    }
    public String getLexeme() {
        return lexeme;
    }
    public LexemeDescriptor getLexemeDescriptor() {
        return lexemeDescriptor;
    }
    public String getPunctuation() {
        return punctuation;
    }
}
