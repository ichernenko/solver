package morphologicAnalysis;

import java.util.List;

public interface MorphologicAnalysis {
    List<Sentence> getSentences(String text);
    String getSolution(List<Sentence> sentences);
}

