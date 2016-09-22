package morphologicalAnalysis;

import textAnalysis.TextBlock;

import java.util.List;

public interface MorphologicAnalysis {
    List<Paragraph> setWordTags(List<TextBlock> textBlocks);
    String getResult(List<TextBlock> textBlocks);
}
