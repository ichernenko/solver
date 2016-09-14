package morphologicalAnalysis;


import textAnalysis.Paragraph;

import java.util.List;

public interface MorphologicAnalysis {
    List<Paragraph> setWordTags(List<Paragraph> paragraphs);
    String getResult(List<Paragraph> paragraphs);
}
