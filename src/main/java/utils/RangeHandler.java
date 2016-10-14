package utils;

import java.util.*;

import static java.util.Arrays.asList;

public class RangeHandler {
    //                             0    1   2   3   4     5     6      всего - 7
    private static String text = "Мама мыла раму а Сережа мыл балкон";
    private static String separator = " ";

    public static void main(String[] args) {
        List<String> words = asList(text.split(separator));
//        Range[] ranges = {new Range(0, words.size())};
        Range[] ranges = new Range[]{new Range(0, 2), new Range(3, 5), new Range(6, 7)};
        List<Range> parts = new LinkedList<>(Arrays.asList(ranges));
        CheckWordMethod<List<String>, Integer> checkWordMethod = RangeHandler::check;

        printAllWords(words);
        processWords(words, parts, checkWordMethod);
        printRangeWords(words, parts);
        printRanges(parts);
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

    public static void processWords(List<String> words, List<Range> parts, CheckWordMethod<List<String>, Integer> checkWordMethod) {
        ListIterator<Range> iterator = parts.listIterator();
        while (iterator.hasNext()) {
            Range range = iterator.next();
            int start = range.getStart();
            int end = range.getEnd();
            int i = start;
            while (i < end) {
                int number = checkWordMethod.check(words, i);
                if (number > 0) {
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
    }

    private static int check(List<String> words, int i) {
        String word = words.get(i);
        return  word.equals("Мама") ||
//                word.equals("мыла") ||
                word.equals("раму") ||
//                word.equals("а") ||
                word.equals("Сережа") ||
                //word.equals("мыл") ||
                word.equals("балкон") ? 1 : 0;
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

class Range {
    private int start;
    private int end;

    Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
    }
}

@FunctionalInterface
interface CheckWordMethod<F, T> {
    int check(F f, T t);
}