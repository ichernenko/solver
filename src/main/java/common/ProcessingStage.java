package common;

import java.util.List;

public interface ProcessingStage {
    void setParameters(List lexemes, int start, int end);
    RangeElementProcessing<Object, Integer>[] getMethods();
}
