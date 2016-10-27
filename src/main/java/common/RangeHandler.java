package common;

import java.util.*;

public class RangeHandler {
    private List<Range> ranges;
    private List inputList;
    private List outputList;

    public RangeHandler(List<Range> ranges, List inputList, List outputList) {
        this.ranges = ranges;
        this.inputList = inputList;
        this.outputList = outputList;
    }

    public List processElements(ProcessingStage processingStage) {
        ListIterator<Range> iterator = ranges.listIterator();
        while (iterator.hasNext()) {
            Range range = iterator.next();
            int start = range.getStart();
            int end = range.getEnd();
            int i = start;

            processingStage.setParameters(inputList, start, end);
            RangeElementProcessing<Object, Integer>[] methods = processingStage.getMethods();

            while (i < end) {
                Object outputElement = null;
                for (int j = 0; j < methods.length && outputElement == null; j++) {
                    outputElement = methods[j].process(i);
                }

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