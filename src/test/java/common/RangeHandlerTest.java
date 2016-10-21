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
        RangeHandler rangeHandler;

        // 1. - (Мама, раму, балкон; 1) - (1,2),(3,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest1);
        correctRanges = new Range[]{new Range(1, 2), new Range(3, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 2. - (Мама, раму, балкон; 2) - (4,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest2);
        correctRanges = new Range[]{new Range(4, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 3. - (Мама, раму, балкон; 3) - (3,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest3);
        correctRanges = new Range[]{new Range(3, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 4. - (Мама, раму, балкон; 4) - (4,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest4);
        correctRanges = new Range[]{new Range(4, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 5. - (Мама, раму, балкон; 5) - (5,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest5);
        correctRanges = new Range[]{new Range(5, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 6. - (Мама, раму, балкон; 6) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest6);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 7. - (Мама, раму, балкон; 7) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest7);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 8. - (Мама, раму, балкон; 8) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest8);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 9. - (мыла, Сережа; 1) - (0,1),(2,4),(5,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest9);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(5, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 10. - (мыла, раму; 1) - (0,1),(3,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest10);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 11. - (мыла, раму; 2) - (0,1),(3,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest11);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 12. - (мыла, раму; 3) - (0,1),(4,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest12);
        correctRanges = new Range[]{new Range(0, 1), new Range(4, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 13. - (мыл, балкон; 1) - (0,5)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest13);
        correctRanges = new Range[]{new Range(0, 5)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 14. - (мыл, балкон; 2) - (0,5)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest14);
        correctRanges = new Range[]{new Range(0, 5)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 15. - (; 1) - (0,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest15);
        correctRanges = new Range[]{new Range(0, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 16. - (Мама; 1) - (1,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest16);
        correctRanges = new Range[]{new Range(1, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 17. - (Мама; 4) - (4,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest17);
        correctRanges = new Range[]{new Range(4, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 18. - (Мама; 8) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest18);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 19. - ((0,2),(3,5),(6,7); Мама, раму, Сережа, балкон; 1) - (1,2),(3,4)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest19);
        correctRanges = new Range[]{new Range(1, 2), new Range(3, 4)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 20. - ((0,2),(3,5),(6,7); мыла, балкон; 1) - (0,1),(3,5)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest20);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 5)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 21. - ((0,2),(3,5),(6,7); Мама, а; 2) - (6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest21);
        correctRanges = new Range[]{new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 22. - ((0,2),(3,5),(6,7); а; 5) - (0,2),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest22);
        correctRanges = new Range[]{new Range(0, 2), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 23. - ((0,2),(3,5),(6,7); ; -) - (0,2),(3,5),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest23);
        correctRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 24. - ((0,2),(3,5),(6,7); Мама, а, балкон; 3) - ()
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest24);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 25. - ((0,2),(3,5),(6,7); а; 2) - (0,2),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest25);
        correctRanges = new Range[]{new Range(0, 2), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 26. - ((0,2),(3,5),(6,7); Мама; 2) - (3,5),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest26);
        correctRanges = new Range[]{new Range(3, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 27. - ((0,2),(3,4),(5,7); мыл; 2) - (3,5),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 4), new Range(5, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest27);
        correctRanges = new Range[]{new Range(0, 2), new Range(3, 4)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 28. - ((0,1),(2,5),(6,7); а; 1) - (0,1),(2,3),(4,5),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest28);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 3), new Range(4, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 29. - ((0,1),(2,5),(6,7); а; 2) - (0,1),(2,3),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest29);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 3), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 30. - ((0,1),(2,5),(6,7); Сережа; 1) - (0,1),(2,4),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest30);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 31. - ((0,1),(2,5),(6,7); Сережа; 2) - (0,1),(2,4),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        rangeHandler.processElements(RangeHandlerTest::processTest31);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));
    }

    private static ElementCountable processTest1(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest2(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest3(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(3) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest4(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(4) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest5(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(5) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest6(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(6) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest7(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(7) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest8(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("балкон") ? new ElementCountableImplTest(8) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest9(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("мыла") || word.equals("Сережа") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest10(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("мыла") || word.equals("раму") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest11(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("мыла") || word.equals("раму") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest12(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("мыла") || word.equals("раму") ? new ElementCountableImplTest(3) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest13(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("мыл") || word.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest14(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("мыл") || word.equals("балкон") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest15(List words, int i, int end) {
        return new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest16(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest17(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") ? new ElementCountableImplTest(4) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest18(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") ? new ElementCountableImplTest(8) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest19(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") || word.equals("раму") || word.equals("Сережа") || word.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest20(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("мыла") || word.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest21(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") || word.equals("а") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest22(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("а") ? new ElementCountableImplTest(5) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest23(List words, int i, int end) {
        String word = (String) words.get(i);
        return new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest24(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") || word.equals("а") || word.equals("балкон") ? new ElementCountableImplTest(3) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest25(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("а") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest26(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Мама") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest27(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("мыл") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest28(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("а") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest29(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("а") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest30(List words, int i, int end) {
        String word = (String) words.get(i);
        return  word.equals("Сережа") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private static ElementCountable processTest31(List words, int i, int end) {
        String word = (String) words.get(i);
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
