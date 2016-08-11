package dictionary;

import java.util.Map;

interface Dictionary {
    Map<String, WordProperty[]> getWordMap();
    Lemma[] getLemmaArray();
}
