package morphologicalAnalysis;

import org.junit.Test;
import textAnalysis.Lexeme;
import textAnalysis.LexemeDescriptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MorphologicProcessingTest {
    Word word;
    MorphologicProcessing morphologicProcessing = new MorphologicProcessing();
    private List<Lexeme> lexemes = new ArrayList<>();
    private int start = 0;
    private int end;

    @Test
    public void processIntegerTest() throws Exception {
        // Тестовый текст: Мама мыла раму
        lexemes.add(new Lexeme(0, "мама", new LexemeDescriptor(false, true, true, true, true, true, false, false), ""));
        lexemes.add(new Lexeme(1, "мыла", new LexemeDescriptor(false, true, false, true, true, true, false, false), ""));
        lexemes.add(new Lexeme(2, "раму", new LexemeDescriptor(false, true, false, true, true, true, false, false), ""));
        end = lexemes.size();
        morphologicProcessing.setParameters(lexemes, start, end);
        word = morphologicProcessing.processInteger(0);
        assertNull(word);
        word = morphologicProcessing.processInteger(1);
        assertNull(word);
        word = morphologicProcessing.processInteger(2);
        assertNull(word);
        word = morphologicProcessing.processInteger(3);
        assertNull(word);
        word = morphologicProcessing.processInteger(4);
        assertNull(word);

        // Тестовый текст: Мама 1
        lexemes.clear();
        lexemes.add(new Lexeme(0, "мама", new LexemeDescriptor(false, true, true, true, true, true, false, false), ""));
        lexemes.add(new Lexeme(1, "1", new LexemeDescriptor(true, false, false, false, false, false, false, false), ""));
        end = lexemes.size();
        morphologicProcessing.setParameters(lexemes, start, end);
        word = morphologicProcessing.processInteger(0);
        assertNull(word);
        word = morphologicProcessing.processInteger(1);
        assertNotNull(word);
        word = morphologicProcessing.processInteger(2);
        assertNull(word);
        word = morphologicProcessing.processInteger(3);
        assertNull(word);

        // Тестовый текст: 9999
        lexemes.clear();
        lexemes.add(new Lexeme(0, "9999", new LexemeDescriptor(true, false, false, false, false, false, false, false), ""));
        end = lexemes.size();
        morphologicProcessing.setParameters(lexemes, start, end);
        word = morphologicProcessing.processInteger(0);
        assertNotNull(word);
        word = morphologicProcessing.processInteger(1);
        assertNull(word);

        // Тестовый текст: 9999 единиц
        lexemes.clear();
        lexemes.add(new Lexeme(0, "9999", new LexemeDescriptor(true, false, false, false, false, false, false, false), ""));
        lexemes.add(new Lexeme(1, "мама", new LexemeDescriptor(false, true, false, true, true, true, false, false), ""));
        end = lexemes.size();
        morphologicProcessing.setParameters(lexemes, start, end);
        word = morphologicProcessing.processInteger(0);
        assertNotNull(word);
        word = morphologicProcessing.processInteger(1);
        assertNull(word);
    }


    private boolean
            hasDigit = false,           // 1
            hasLetter = false,          // 2
            hasFirstUpperCase = false,  // 3
            hasLowerCase = false,       // 4
            hasUpperCase = false,       // 5
            hasRussian = false,         // 6
            hasLatin = false,           // 7
            hasOther = false;           // 8

    @Test
    public void processFractionTest() throws Exception {
        // Тестовый текст: Мама мыла раму
        lexemes.add(new Lexeme(0, "мама", new LexemeDescriptor(false, true, true, true, true, true, false, false), ""));
        lexemes.add(new Lexeme(1, "мыла", new LexemeDescriptor(false, true, false, true, true, true, false, false), ""));
        lexemes.add(new Lexeme(2, "раму", new LexemeDescriptor(false, true, false, true, true, true, false, false), ""));
        end = lexemes.size();
        morphologicProcessing.setParameters(lexemes, start, end);
        word = morphologicProcessing.processFraction(0);
        assertNull(word);
        word = morphologicProcessing.processFraction(1);
        assertNull(word);
        word = morphologicProcessing.processFraction(2);
        assertNull(word);
        word = morphologicProcessing.processFraction(3);
        assertNull(word);
        word = morphologicProcessing.processFraction(4);
        assertNull(word);

        // Тестовый текст: Мама 1,4
        lexemes.clear();
        lexemes.add(new Lexeme(0, "мама", new LexemeDescriptor(false, true, true, true, true, true, false, false), ""));
        lexemes.add(new Lexeme(1, "1", new LexemeDescriptor(true, false, false, false, false, false, false, false), ","));
        lexemes.add(new Lexeme(2, "4", new LexemeDescriptor(true, false, false, false, false, false, false, false), ""));
        end = lexemes.size();
        morphologicProcessing.setParameters(lexemes, start, end);
        word = morphologicProcessing.processFraction(0);
        assertNull(word);
        word = morphologicProcessing.processFraction(1);
        assertNotNull(word);
        word = morphologicProcessing.processFraction(2);
        assertNull(word);
        word = morphologicProcessing.processFraction(3);
        assertNull(word);

        // Тестовый текст: 9999,555
        lexemes.clear();
        lexemes.add(new Lexeme(0, "9999", new LexemeDescriptor(true, false, false, false, false, false, false, false), ","));
        lexemes.add(new Lexeme(1, "555", new LexemeDescriptor(true, false, false, false, false, false, false, false), ""));
        end = lexemes.size();
        morphologicProcessing.setParameters(lexemes, start, end);
        word = morphologicProcessing.processFraction(0);
        assertNotNull(word);
        word = morphologicProcessing.processFraction(1);
        assertNull(word);
        word = morphologicProcessing.processFraction(2);
        assertNull(word);

        // Тестовый текст: 9999,555 единиц
        lexemes.clear();
        lexemes.add(new Lexeme(0, "9999", new LexemeDescriptor(true, false, false, false, false, false, false, false), ","));
        lexemes.add(new Lexeme(1, "555", new LexemeDescriptor(true, false, false, false, false, false, false, false), ""));
        lexemes.add(new Lexeme(2, "единиц", new LexemeDescriptor(false, true, false, true, true, true, false, false), ""));
        end = lexemes.size();
        morphologicProcessing.setParameters(lexemes, start, end);
        word = morphologicProcessing.processFraction(0);
        assertNotNull(word);
        word = morphologicProcessing.processFraction(1);
        assertNull(word);
        word = morphologicProcessing.processFraction(2);
        assertNull(word);
        word = morphologicProcessing.processFraction(3);
        assertNull(word);
    }
}