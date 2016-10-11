package utils;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        List<String> words = initWords();
        List<Range> parts = initParts(words);
        printAllWords(words);
        processWords(words, parts);
        printRangeWords(words, parts);
    }

    private static List<String> initWords() {
        List<String> words = new ArrayList<>();
        words.add("Мама");
        words.add("мыла");
        words.add("раму");
        words.add("а");
        words.add("Сережа");
        words.add("мыл");
        words.add("балкон");
        return words;
    }

    private static List<Range> initParts(List<String> words) {
        List<Range> parts = new LinkedList<>();
//        parts.add(new Range(0, 1));
//        parts.add(new Range(2, words.size()));
        parts.add(new Range(0, words.size()));
        return parts;
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

    private static void processWords(List<String> words, List<Range> parts) {
        ListIterator<Range> iterator = parts.listIterator();
        while (iterator.hasNext()) {
            Range range = iterator.next();
            int start = range.getStart();
            int end = range.getEnd();
            for (int i = start; i < end; i++) {
                String word = words.get(i);
                if (check(word)) {
                    addPartRange(iterator, i, end);
                }
            }
        }
    }

    private static boolean check(String word) {
        return //word.equals("Мама") ||
                word.equals("мыла") ||
                word.equals("раму") ||
                word.equals("а") ||
                word.equals("Сережа") ||
                word.equals("мыл");// ||
                //word.equals("балкон");
    }

    private static void addPartRange(ListIterator<Range> iterator, int index, int end) {
        Range previousRange = iterator.previous();
        previousRange.setEnd(index);
        iterator.set(previousRange);
        iterator.next();
        iterator.add(new Range(index + 1, end));
    }

    private static void printRangeWords(List<String> words, List<Range> parts) {
        ListIterator<Range> iterator = parts.listIterator();
        while (iterator.hasNext()) {
            Range range = iterator.next();
            int start = range.getStart();
            int end = range.getEnd();
            for (int i = start; i < end; i++) {
                String word = words.get(i);
                System.out.print(word + "(" + i + ") ");
            }
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
