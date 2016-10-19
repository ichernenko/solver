package common;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;


public class RangeHandlerTest {
    //                             0    1   2   3   4     5     6      всего - 7
    private static String text = "Мама мыла раму а Сережа мыл балкон";
    private static String separator = " ";

    @Test
    public void processWordsTest() throws Exception {
        List<String> inputList = Arrays.asList(text.split(separator));;
        List outputList = new ArrayList<>(inputList.size());
        Range[] inputRanges;
        Range[] correctRanges;
        List<Range> ranges;

        // 1. - (Мама, раму, балкон; 1) - (1,2),(3,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest1, inputList, outputList);
        correctRanges = new Range[]{new Range(1, 2), new Range(3, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 2. - (Мама, раму, балкон; 2) - (4,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest2, inputList, outputList);
        correctRanges = new Range[]{new Range(4, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 3. - (Мама, раму, балкон; 3) - (3,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest3, inputList, outputList);
        correctRanges = new Range[]{new Range(3, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 4. - (Мама, раму, балкон; 4) - (4,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest4, inputList, outputList);
        correctRanges = new Range[]{new Range(4, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 5. - (Мама, раму, балкон; 5) - (5,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest5, inputList, outputList);
        correctRanges = new Range[]{new Range(5, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 6. - (Мама, раму, балкон; 6) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest6, inputList, outputList);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 7. - (Мама, раму, балкон; 7) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest7, inputList, outputList);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 8. - (Мама, раму, балкон; 8) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest8, inputList, outputList);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 9. - (мыла, Сережа; 1) - (0,1),(2,4),(5,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest9, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(5, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 10. - (мыла, раму; 1) - (0,1),(3,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest10, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 11. - (мыла, раму; 2) - (0,1),(3,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest11, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 12. - (мыла, раму; 3) - (0,1),(4,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest12, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 1), new Range(4, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 13. - (мыл, балкон; 1) - (0,5)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest13, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 5)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 14. - (мыл, балкон; 2) - (0,5)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest14, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 5)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 15. - (; 1) - (0,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest15, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 16. - (Мама; 1) - (1,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest16, inputList, outputList);
        correctRanges = new Range[]{new Range(1, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 17. - (Мама; 4) - (4,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest17, inputList, outputList);
        correctRanges = new Range[]{new Range(4, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 18. - (Мама; 8) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest18, inputList, outputList);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 19. - ((0,2),(3,5),(6,7); Мама, раму, Сережа, балкон; 1) - (1,2),(3,4)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest19, inputList, outputList);
        correctRanges = new Range[]{new Range(1, 2), new Range(3, 4)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 20. - ((0,2),(3,5),(6,7); мыла, балкон; 1) - (0,1),(3,5)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest20, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 5)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 21. - ((0,2),(3,5),(6,7); Мама, а; 2) - (6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest21, inputList, outputList);
        correctRanges = new Range[]{new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 22. - ((0,2),(3,5),(6,7); а; 5) - (0,2),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest22, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 2), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 23. - ((0,2),(3,5),(6,7); ; -) - (0,2),(3,5),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest23, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 24. - ((0,2),(3,5),(6,7); Мама, а, балкон; 3) - ()
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest24, inputList, outputList);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 25. - ((0,2),(3,5),(6,7); а; 2) - (0,2),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest25, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 2), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 26. - ((0,2),(3,5),(6,7); Мама; 2) - (3,5),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest26, inputList, outputList);
        correctRanges = new Range[]{new Range(3, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 27. - ((0,2),(3,4),(5,7); мыл; 2) - (3,5),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 4), new Range(5, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest27, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 2), new Range(3, 4)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 28. - ((0,1),(2,5),(6,7); а; 1) - (0,1),(2,3),(4,5),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest28, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 3), new Range(4, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 29. - ((0,1),(2,5),(6,7); а; 2) - (0,1),(2,3),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest29, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 3), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 30. - ((0,1),(2,5),(6,7); Сережа; 1) - (0,1),(2,4),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest30, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 31. - ((0,1),(2,5),(6,7); Сережа; 2) - (0,1),(2,4),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        RangeHandler.processElements(ranges, RangeHandlerTest::processTest31, inputList, outputList);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));
    }

    private static ElementCountable processTest1(Object word) {
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest2(Object word) {
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest3(Object word) {
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(3) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest4(Object word) {
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(4) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest5(Object word) {
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(5) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest6(Object word) {
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(6) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest7(Object word) {
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(7) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest8(Object word) {
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(8) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest9(Object word) {
        return  word.equals("мыла") || word.equals("Сережа") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest10(Object word) {
        return  word.equals("мыла") || word.equals("раму") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest11(Object word) {
        return  word.equals("мыла") || word.equals("раму") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest12(Object word) {
        return  word.equals("мыла") || word.equals("раму") ? new ElementCountableImplTest(3) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest13(Object word) {
        return  word.equals("мыл") || word.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest14(Object word) {
        return  word.equals("мыл") || word.equals("балкон") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest15(Object word) {
        return new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest16(Object word) {
        return  word.equals("Мама") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest17(Object word) {
        return  word.equals("Мама") ? new ElementCountableImplTest(4) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest18(Object word) {
        return  word.equals("Мама") ? new ElementCountableImplTest(8) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest19(Object word) {
        return  word.equals("Мама") || word.equals("раму") || word.equals("Сережа") || word.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest20(Object word) {
        return  word.equals("мыла") || word.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest21(Object word) {
        return  word.equals("Мама") || word.equals("а") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest22(Object word) {
        return  word.equals("а") ? new ElementCountableImplTest(5) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest23(Object word) {
        return new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest24(Object word) {
        return  word.equals("Мама") || word.equals("а") || word.equals("балкон") ? new ElementCountableImplTest(3) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest25(Object word) {
        return  word.equals("а") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest26(Object word) {
        return  word.equals("Мама") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest27(Object word) {
        return  word.equals("мыл") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest28(Object word) {
        return  word.equals("а") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest29(Object word) {
        return  word.equals("а") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest30(Object word) {
        return  word.equals("Сережа") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest31(Object word) {
        return  word.equals("Сережа") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
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

    private static void printAllWords(List<String> words) {
        ListIterator<String> iterator = words.listIterator();
        while (iterator.hasNext()) {
            int i = iterator.nextIndex();
            String word = iterator.next();
            System.out.print(word + "(" + i + ") ");
        }
        System.out.println();
    }

    private static void printRangeWords(List<String> words, List<Range> parts) {
        for (Range range : parts) {
            int start = range.getStart();
            int end = range.getEnd();
            for (int i = start; i < end; i++) {
                String word = words.get(i);
                System.out.print(word + "(" + i + ") ");
            }
        }
    }

    public static void printRanges(List<Range> parts) {
        ListIterator<Range> iterator = parts.listIterator();
        System.out.println();
        while (iterator.hasNext()) {
            Range range = iterator.next();
            int start = range.getStart();
            int end = range.getEnd();
            System.out.print("(" + start + "," + end + ") ");
        }
    }
}

class ElementCountableImplTest implements ElementCountable {
    private int elementNumber;

    ElementCountableImplTest(int elementNumber) {
        this.elementNumber = elementNumber;
    }

    @Override
    public int getElementNumber() {
        return elementNumber;
    }
}
