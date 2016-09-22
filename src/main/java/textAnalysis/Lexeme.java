package textAnalysis;

import java.io.Serializable;

public class Lexeme  implements Serializable {
    private String lexeme;
    private LexemeDescriptor lexemeDescriptor;


    Lexeme(String lexeme, LexemeDescriptor lexemeDescriptor) {
        this.lexeme = lexeme;
        this.lexemeDescriptor = lexemeDescriptor;
    }

    public String getLexeme() {
        return lexeme;
    }
    public LexemeDescriptor getLexemeDescriptor() {
        return lexemeDescriptor;
    }
}
