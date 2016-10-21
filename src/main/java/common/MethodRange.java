package common;

import java.util.List;

public class MethodRange {
    private int elementsNumber;
    private RangeElementProcessing<Object, List, Integer> rangeElementProcessing;

    public MethodRange(int elementsNumber, RangeElementProcessing<Object, List, Integer> rangeElementProcessing) {
        this.elementsNumber = elementsNumber;
        this.rangeElementProcessing = rangeElementProcessing;
    }

    public int getElementsNumber() {
        return elementsNumber;
    }
    public RangeElementProcessing<Object, List, Integer> getRangeElementProcessing() {
        return rangeElementProcessing;
    }
}
