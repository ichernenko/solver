package tokenization;

import java.util.Map;

public interface Dictionary {
    Map<String, WordProperty[]> getWordMap();
    Lemma[] getLemmas();
}
