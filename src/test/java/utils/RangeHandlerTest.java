package utils;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class RangeHandlerTest {
    //                             0    1   2   3   4     5     6      всего - 7
    private static String text = "Мама мыла раму а Сережа мыл балкон";
    private static String separator = " ";
    int wordNumber;

    @Test
    public void processWordsTest() throws Exception {
        List<String> words = Arrays.asList(text.split(separator));;
        Range[] ranges;
        Range[] correctRanges;
        List<Range> parts;
        CheckWordMethod<List<String>, Integer> checkWordMethod;

        // 1. - (Мама, раму, балкон; 1) - (1,2),(3,6)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest1;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(1, 2), new Range(3, 6)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 2. - (Мама, раму, балкон; 2) - (4,6)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest2;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(4, 6)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 3. - (Мама, раму, балкон; 3) - (3,6)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest3;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(3, 6)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 4. - (Мама, раму, балкон; 4) - (4,6)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest4;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(4, 6)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 5. - (Мама, раму, балкон; 5) - (5,6)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest5;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(5, 6)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 6. - (Мама, раму, балкон; 6) - ()
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest6;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 7. - (Мама, раму, балкон; 7) - ()
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest7;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 8. - (Мама, раму, балкон; 8) - ()
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest8;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 9. - (мыла, Сережа; 1) - (0,1),(2,4),(5,7)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest9;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(5, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 10. - (мыла, раму; 1) - (0,1),(3,7)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest10;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 11. - (мыла, раму; 2) - (0,1),(3,7)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest11;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 12. - (мыла, раму; 3) - (0,1),(4,7)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest12;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 1), new Range(4, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 13. - (мыл, балкон; 1) - (0,5)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest13;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 5)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 14. - (мыл, балкон; 2) - (0,5)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest14;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 5)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 15. - (; 1) - (0,7)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest15;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 16. - (Мама; 1) - (1,7)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest16;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(1, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 17. - (Мама; 4) - (4,7)
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest17;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(4, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 18. - (Мама; 8) - ()
        ranges = new Range[]{new Range(0, words.size())};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest18;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 19. - ((0,2),(3,5),(6,7); Мама, раму, Сережа, балкон; 1) - (1,2),(3,4)
        ranges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest19;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(1, 2), new Range(3, 4)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 20. - ((0,2),(3,5),(6,7); мыла, балкон; 1) - (0,1),(3,5)
        ranges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest20;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 5)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 21. - ((0,2),(3,5),(6,7); Мама, а; 2) - (6,7)
        ranges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest21;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(6, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 22. - ((0,2),(3,5),(6,7); а; 5) - (0,2),(6,7)
        ranges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest22;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 2), new Range(6, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 23. - ((0,2),(3,5),(6,7); ; -) - (0,2),(3,5),(6,7)
        ranges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest23;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 24. - ((0,2),(3,5),(6,7); Мама, а, балкон; 3) - ()
        ranges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest24;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 25. - ((0,2),(3,5),(6,7); а; 2) - (0,2),(6,7)
        ranges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest25;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 2), new Range(6, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 26. - ((0,2),(3,5),(6,7); Мама; 2) - (3,5),(6,7)
        ranges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest26;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(3, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 27. - ((0,2),(3,4),(5,7); мыл; 2) - (3,5),(6,7)
        ranges = new Range[]{new Range(0, 2), new Range(3, 4), new Range(5, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest27;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 2), new Range(3, 4)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 28. - ((0,1),(2,5),(6,7); а; 1) - (0,1),(2,3),(4,5),(6,7)
        ranges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest28;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 3), new Range(4, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 29. - ((0,1),(2,5),(6,7); а; 2) - (0,1),(2,3),(6,7)
        ranges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest29;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 3), new Range(6, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 30. - ((0,1),(2,5),(6,7); Сережа; 1) - (0,1),(2,4),(6,7)
        ranges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest30;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(6, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));

        // 31. - ((0,1),(2,5),(6,7); Сережа; 2) - (0,1),(2,4),(6,7)
        ranges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        parts = new LinkedList<>(Arrays.asList(ranges));
        checkWordMethod = RangeHandlerTest::checkTest31;
        RangeHandler.processWords(words, parts, checkWordMethod);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(6, 7)};
        assertTrue(isEqualRanges(parts, correctRanges));
    }

    private static int checkTest1(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? 1 : 0;
    }

    private static int checkTest2(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? 2 : 0;
    }

    private static int checkTest3(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? 3 : 0;
    }

    private static int checkTest4(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? 4 : 0;
    }

    private static int checkTest5(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? 5 : 0;
    }

    private static int checkTest6(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? 6 : 0;
    }

    private static int checkTest7(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? 7 : 0;
    }

    private static int checkTest8(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? 8 : 0;
    }

    private static int checkTest9(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("мыла") || word.equals("Сережа") ? 1 : 0;
    }

    private static int checkTest10(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("мыла") || word.equals("раму") ? 1 : 0;
    }

    private static int checkTest11(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("мыла") || word.equals("раму") ? 2 : 0;
    }

    private static int checkTest12(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("мыла") || word.equals("раму") ? 3 : 0;
    }

    private static int checkTest13(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("мыл") || word.equals("балкон") ? 1 : 0;
    }

    private static int checkTest14(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("мыл") || word.equals("балкон") ? 2 : 0;
    }

    private static int checkTest15(List<String> words, int i) {
        return 0;
    }

    private static int checkTest16(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") ? 1 : 0;
    }

    private static int checkTest17(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") ? 4 : 0;
    }

    private static int checkTest18(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") ? 8 : 0;
    }

    private static int checkTest19(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("Сережа") || word.equals("балкон") ? 1 : 0;
    }

    private static int checkTest20(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("мыла") || word.equals("балкон") ? 1 : 0;
    }

    private static int checkTest21(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") || word.equals("а") ? 2 : 0;
    }

    private static int checkTest22(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("а") ? 5 : 0;
    }

    private static int checkTest23(List<String> words, int i) {
        return 0;
    }

    private static int checkTest24(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") || word.equals("а") || word.equals("балкон") ? 3 : 0;
    }

    private static int checkTest25(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("а") ? 2 : 0;
    }

    private static int checkTest26(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") ? 2 : 0;
    }

    private static int checkTest27(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("мыл") ? 2 : 0;
    }

    private static int checkTest28(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("а") ? 1 : 0;
    }

    private static int checkTest29(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("а") ? 2 : 0;
    }

    private static int checkTest30(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Сережа") ? 1 : 0;
    }

    private static int checkTest31(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Сережа") ? 2 : 0;
    }

    private boolean isEqualRanges(List<Range> parts, Range[] ranges) {
        if (parts.size() != ranges.length) {
            return false;
        }
        for (int i = 0; i < ranges.length; i++) {
            if (ranges[i].getStart() != parts.get(i).getStart() || ranges[i].getEnd() != parts.get(i).getEnd()) {
                return false;
            }
        }
        return true;
    }
}