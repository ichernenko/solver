package textStructureDefinition;

import java.util.List;

public interface TextParser {
    List<Sentence> getSentences(String text);
}
