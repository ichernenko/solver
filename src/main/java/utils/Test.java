package utils;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        List<String> words = initWords();
        List<Range> parts = initParts(words);
        printAllWords(words);
        processWords(words, parts);
        printRangeWords(words, parts);
        printRanges(parts);
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
            int i = start;
            while (i < end) {
                int number = check(words.get(i));
                if (number > 0) {
                    //i = processRange(iterator, range, i, number, start, end);
                    if (i == start) {
                        if (i + number == end) {
                            iterator.remove();
                            break;
                        } else {
                            i += number;
                            range.setStart(i);
                            iterator.set(range);
                            start = i;
                        }
                    } else {
                        if (i + number >= end) {
                            range.setEnd(i);
                            iterator.set(range);
                            end = i;
                        } else {
                            range.setEnd(i);
                            iterator.set(range);
                            i += number;
                            iterator.add(new Range(i, end));
                        }
                    }
                } else {
                    i++;
                }
            }
        }
    }

    private static int check(String word) {
        return  word.equals("Мама") ||
//                word.equals("мыла") ||
                word.equals("раму") ||
//                word.equals("а") ||
                //word.equals("Сережа") ||
                //word.equals("мыл") //||
                word.equals("балкон") ? 2 : 0;
    }

    private static int processRange(ListIterator<Range> iterator, Range range, int index, int number, int start, int end) {
        // При изменении крайних точек необходимо изменять и начало/конец диапазона
        if (index == start) {
            if (index + number == end) {
                iterator.remove();
                return end;
            } else {
                index = index + number;
                range.setStart(index);
                iterator.set(range);
                return index;
            }
        } else {
            if (index + number >= end) {
                range.setEnd(index);
                iterator.set(range);
                return end;
            } else {
                range.setEnd(index);
                iterator.set(range);
                index = index + number;
                iterator.add(new Range(index, end));
                return index;
            }
        }
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

    private static void printRanges(List<Range> parts) {
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
