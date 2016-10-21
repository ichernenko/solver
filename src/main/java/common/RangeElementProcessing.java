package common;

@FunctionalInterface
public interface RangeElementProcessing<O, L, I> {
    O process(L l2, I i1, I i2);
}
