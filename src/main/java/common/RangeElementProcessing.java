package common;

@FunctionalInterface
public interface RangeElementProcessing<O, E> {
    E process(O o);
}
