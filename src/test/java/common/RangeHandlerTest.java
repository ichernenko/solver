package common;

import morphologicalAnalysis.MorphologicAnalysisImpl;
import morphologicalAnalysis.MorphologicProcessing;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class RangeHandlerTest {
    //                             0    1   2   3   4     5     6      всего - 7
    private static String text = "Мама мыла раму а Сережа мыл балкон";
    private static String separator = " ";
    private static List<String> inputList = Arrays.asList(text.split(separator));

    @Test
    public void processWordsTest() throws Exception {
        List outputList = new ArrayList<>(inputList.size());
        Range[] inputRanges;
        Range[] correctRanges;
        List<Range> ranges;
        RangeHandler rangeHandler;
        RangeElementProcessing<Object, Integer> processTest;
        MorphologicProcessing morphologicProcessing = new MorphologicProcessing();

        // 1. - (Мама, раму, балкон; 1) - (1,2),(3,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest1});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(1, 2), new Range(3, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 2. - (Мама, раму, балкон; 2) - (4,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest2});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(4, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 3. - (Мама, раму, балкон; 3) - (3,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest3});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(3, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 4. - (Мама, раму, балкон; 4) - (4,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest4});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(4, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 5. - (Мама, раму, балкон; 5) - (5,6)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest5});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(5, 6)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 6. - (Мама, раму, балкон; 6) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest6});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 7. - (Мама, раму, балкон; 7) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest7});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 8. - (Мама, раму, балкон; 8) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest8});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 9. - (мыла, Сережа; 1) - (0,1),(2,4),(5,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest9});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(5, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 10. - (мыла, раму; 1) - (0,1),(3,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest10});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 11. - (мыла, раму; 2) - (0,1),(3,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest11});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 12. - (мыла, раму; 3) - (0,1),(4,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest12});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 1), new Range(4, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 13. - (мыл, балкон; 1) - (0,5)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest13});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 5)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 14. - (мыл, балкон; 2) - (0,5)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest14});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 5)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 15. - (; 1) - (0,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest15});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 16. - (Мама; 1) - (1,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest16});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(1, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 17. - (Мама; 4) - (4,7)
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest17});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(4, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 18. - (Мама; 8) - ()
        inputRanges = new Range[]{new Range(0, inputList.size())};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest18});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 19. - ((0,2),(3,5),(6,7); Мама, раму, Сережа, балкон; 1) - (1,2),(3,4)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest19});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(1, 2), new Range(3, 4)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 20. - ((0,2),(3,5),(6,7); мыла, балкон; 1) - (0,1),(3,5)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest20});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 1), new Range(3, 5)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 21. - ((0,2),(3,5),(6,7); Мама, а; 2) - (6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest21});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 22. - ((0,2),(3,5),(6,7); а; 5) - (0,2),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest22});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 2), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 23. - ((0,2),(3,5),(6,7); ; -) - (0,2),(3,5),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest23});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 24. - ((0,2),(3,5),(6,7); Мама, а, балкон; 3) - ()
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest24});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 25. - ((0,2),(3,5),(6,7); а; 2) - (0,2),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest25});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 2), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 26. - ((0,2),(3,5),(6,7); Мама; 2) - (3,5),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest26});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(3, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 27. - ((0,2),(3,4),(5,7); мыл; 2) - (3,5),(6,7)
        inputRanges = new Range[]{new Range(0, 2), new Range(3, 4), new Range(5, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest27});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 2), new Range(3, 4)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 28. - ((0,1),(2,5),(6,7); а; 1) - (0,1),(2,3),(4,5),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest28});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 3), new Range(4, 5), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 29. - ((0,1),(2,5),(6,7); а; 2) - (0,1),(2,3),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest29});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 3), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 30. - ((0,1),(2,5),(6,7); Сережа; 1) - (0,1),(2,4),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest30});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));

        // 31. - ((0,1),(2,5),(6,7); Сережа; 2) - (0,1),(2,4),(6,7)
        inputRanges = new Range[]{new Range(0, 1), new Range(2, 5), new Range(6, 7)};
        ranges = new LinkedList<>(Arrays.asList(inputRanges));
        rangeHandler = new RangeHandler(ranges, inputList, outputList);
        morphologicProcessing.setMethods(new RangeElementProcessing[]{this::processTest31});
        rangeHandler.processElements(morphologicProcessing);
        correctRanges = new Range[]{new Range(0, 1), new Range(2, 4), new Range(6, 7)};
        assertTrue(isEqualRanges(ranges, correctRanges));
    }

    private ElementCountable processTest1(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") || element.equals("раму") || element.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest2(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") || element.equals("раму") || element.equals("балкон") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest3(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") || element.equals("раму") || element.equals("балкон") ? new ElementCountableImplTest(3) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest4(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") || element.equals("раму") || element.equals("балкон") ? new ElementCountableImplTest(4) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest5(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") || element.equals("раму") || element.equals("балкон") ? new ElementCountableImplTest(5) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest6(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") || element.equals("раму") || element.equals("балкон") ? new ElementCountableImplTest(6) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest7(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") || element.equals("раму") || element.equals("балкон") ? new ElementCountableImplTest(7) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest8(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") || element.equals("раму") || element.equals("балкон") ? new ElementCountableImplTest(8) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest9(int i) {
        String element = inputList.get(i);
        return  element.equals("мыла") || element.equals("Сережа") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest10(int i) {
        String element = inputList.get(i);
        return  element.equals("мыла") || element.equals("раму") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest11(int i) {
        String element = inputList.get(i);
        return  element.equals("мыла") || element.equals("раму") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest12(int i) {
        String element = inputList.get(i);
        return  element.equals("мыла") || element.equals("раму") ? new ElementCountableImplTest(3) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest13(int i) {
        String element = inputList.get(i);
        return  element.equals("мыл") || element.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest14(int i) {
        String element = inputList.get(i);
        return  element.equals("мыл") || element.equals("балкон") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest15(int i) {
        return new ElementCountableImplTest(0);
    }

    private ElementCountable processTest16(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest17(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") ? new ElementCountableImplTest(4) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest18(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") ? new ElementCountableImplTest(8) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest19(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") || element.equals("раму") || element.equals("Сережа") || element.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest20(int i) {
        String element = inputList.get(i);
        return  element.equals("мыла") || element.equals("балкон") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest21(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") || element.equals("а") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest22(int i) {
        String element = inputList.get(i);
        return  element.equals("а") ? new ElementCountableImplTest(5) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest23(int i) {
        String element = inputList.get(i);
        return new ElementCountableImplTest(0);
    }

    private ElementCountable processTest24(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") || element.equals("а") || element.equals("балкон") ? new ElementCountableImplTest(3) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest25(int i) {
        String element = inputList.get(i);
        return  element.equals("а") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest26(int i) {
        String element = inputList.get(i);
        return  element.equals("Мама") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest27(int i) {
        String element = inputList.get(i);
        return  element.equals("мыл") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest28(int i) {
        String element = inputList.get(i);
        return  element.equals("а") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest29(int i) {
        String element = inputList.get(i);
        return  element.equals("а") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest30(int i) {
        String element = inputList.get(i);
        return  element.equals("Сережа") ? new ElementCountableImplTest(1) : new ElementCountableImplTest(0);
    }

    private ElementCountable processTest31(int i) {
        String element = inputList.get(i);
        return  element.equals("Сережа") ? new ElementCountableImplTest(2) : new ElementCountableImplTest(0);
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

    private void printAllElements(List<String> inputList) {
        ListIterator<String> iterator = inputList.listIterator();
        while (iterator.hasNext()) {
            int i = iterator.nextIndex();
            String element = iterator.next();
            System.out.print(element + "(" + i + ") ");
        }
        System.out.println();
    }

    private void printRangeElements(List<String> inputList, List<Range> parts) {
        for (Range range : parts) {
            int start = range.getStart();
            int end = range.getEnd();
            for (int i = start; i < end; i++) {
                String element = inputList.get(i);
                System.out.print(element + "(" + i + ") ");
            }
        }
    }

    public void printRanges(List<Range> parts) {
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
