package common;

import java.util.*;

public class RangeHandler {
    public static List processElements(List<Range> ranges, RangeElementProcessing<Object, Object> rangeElementProcessing, List inputList, List outputList) {
        ListIterator<Range> iterator = ranges.listIterator();
        while (iterator.hasNext()) {
            Range range = iterator.next();
            int start = range.getStart();
            int end = range.getEnd();
            int i = start;
            while (i < end) {
                Object inputElement = inputList.get(i);
                Object outputElement = rangeElementProcessing.process(inputElement);

                int number;
                if (outputElement != null && (number = ((ElementCountable) outputElement).getElementNumber()) > 0) {
                    outputList.add(outputElement);

                    if (i == start) {
                        if (i + number >= end) {
                            iterator.remove();
                            break;
                        } else {
                            i += number;
                            range.setStart(i);
                            iterator.set(range);
                            start = i;
                        }
                    } else {
                        range.setEnd(i);
                        iterator.set(range);
                        if (i + number < end) {
                            iterator.add(new Range(i + number, end));
                            iterator.previous();
                        }
                        break;
                    }
                } else {
                    i++;
                }
            }
        }
        return outputList;
    }
}