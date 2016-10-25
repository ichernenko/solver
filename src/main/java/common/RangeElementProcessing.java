package common;

@FunctionalInterface
public interface RangeElementProcessing<O, L, I> {
    O process(L l, I i, I s, I e);
}
