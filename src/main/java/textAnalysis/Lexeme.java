package textAnalysis;

public class Lexeme {
    private String lexeme;
    private String punctuations;
    private LexemeDescriptor lexemeDescriptor;


    Lexeme(String lexeme, String punctuations, LexemeDescriptor lexemeDescriptor) {
        this.lexeme = lexeme;
        this.punctuations = punctuations;
        this.lexemeDescriptor = lexemeDescriptor;
    }

    public String getLexeme() {
        return lexeme;
    }
    public String getPunctuations() {
        return punctuations;
    }
    public LexemeDescriptor getLexemeDescriptor() {
        return lexemeDescriptor;
    }
}
