package morphologicAnalysis;


import textAnalysis.Sentence;

import java.util.List;

public interface MorphologicAnalysis {
    List<Sentence> setWordTags(List<Sentence> sentences);
    String getResult(List<Sentence> sentences);
}
