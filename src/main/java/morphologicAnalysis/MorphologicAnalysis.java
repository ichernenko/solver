package morphologicAnalysis;

import dictionaryLoading.Lemma;

import java.util.List;

public interface MorphologicAnalysis {
    List<Sentence> getSentences(String text);
    Lemma getLemma(int lemmaId);
}

