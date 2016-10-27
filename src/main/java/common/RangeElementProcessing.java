package common;

import java.util.List;

@FunctionalInterface
public interface RangeElementProcessing<O, I> {
    Object process(int i);
}
