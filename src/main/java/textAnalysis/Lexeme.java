package textAnalysis;

import java.io.Serializable;

public class Lexeme implements Serializable {
    private int order;
    private String lexeme;
    private LexemeDescriptor lexemeDescriptor;


    Lexeme(int order, String lexeme, LexemeDescriptor lexemeDescriptor) {
        this.order = order;
        this.lexeme = lexeme;
        this.lexemeDescriptor = lexemeDescriptor;
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
}
