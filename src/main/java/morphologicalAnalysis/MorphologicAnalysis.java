package morphologicalAnalysis;

import textAnalysis.TextBlock;

import java.util.List;

public interface MorphologicAnalysis {
    List<Paragraph> getParagraphs(List<TextBlock> textBlocks);
    String getResult(List<Paragraph> paragraphs);
}
