package morphologicAnalysis;

import java.util.ArrayList;
import java.util.List;
import dictionaryLoading.DictionaryLoading;
import dictionaryLoading.Lemma;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import dictionaryLoading.WordProperty;
import preliminaryTextProcessing.PreliminaryTextProcessing;

public class MorphologicAnalysisImpl implements MorphologicAnalysis {

    // Метод разбивает текст на предложения и возвращает список этих предложений с характеристиками предложений.
    // Предложением является список лексем, оканчивающийся '.','!','?','¡'(?!)
    // Списки предложений состоят из списков слов и списков пунктуаций.
    @Override
    public List<Sentence> getSentences(String text) {
        // Выполнение неотъемлемоего предыдущего уровня, а именно
        // уровня предварительной обработки текста
        text = PreliminaryTextProcessing.processText(text);

        List<Sentence> sentences = new ArrayList<>();

        if (text != null && text.length() > 0) {
            List<Word> words = new ArrayList<>();
            List<Punctuation> punctuationMarks = new ArrayList<>();
            StringBuilder token = new StringBuilder();

            int wordNumber = 0;
            boolean isSentence = false;
            boolean isWord = false;

            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                if (ch == '.' || ch == '!' || ch == '?' || ch == '…' || ch == '¡') {
                    words.add(getWord(token.toString()));
                    punctuationMarks.add(new Punctuation(ch, wordNumber));
                    sentences.add(new Sentence(words, punctuationMarks));
                    isWord = false;
                    isSentence = false;
                } else {
                    if (ch == ',' || ch == ';' || ch == ':' || ch == '(' || ch == '{' || ch == '[' || ch == ')' || ch == '}' || ch == ']' || ch == '—' || ch == '«' || ch == '»') {
                        words.add(getWord(token.toString()));
                        punctuationMarks.add(new Punctuation(ch, wordNumber));
                        isWord = false;
                    } else {
                        if (ch == ' ') {
                            words.add(getWord(token.toString()));
                            isWord = false;
                        } else {
                            if (!isSentence) {
                                words = new ArrayList<>();
                                punctuationMarks = new ArrayList<>();
                                token.setLength(0);
                                wordNumber = 0;
                                isWord = true;
                                isSentence = true;
                            } else {
                                if (!isWord) {
                                    token.setLength(0);
                                    wordNumber ++;
                                    isWord = true;
                                }
                            }
                            token.append(ch);
                        }
                    }
                }
            }
        }
        return sentences;
    }

    // Метод возвращает слово с тегами из словаря
    private static Word getWord(String word) {
        // Вначале проверяем не является ли последовательность слов идиомой
        WordProperty[] wordProperties = DictionaryLoading.getWordDictionary().get(word);
        WordTag[] wordTags = createWordTags(wordProperties);

        return new Word(word, wordTags);
    }


    // Метод создает массив WordTags из массива WordProperty
    // TODO: подумать!!!
    // Если бы изначально словарь все слова создавал бы с WordTags этого б делать не следовало
    private static WordTag[] createWordTags(WordProperty[] wordProperties) {
        if (wordProperties == null)
            return new WordTag[]{};

        WordTag[] wordTags = new WordTag[wordProperties.length];
        for (int i = 0; i < wordProperties.length; i++) {
            wordTags[i] = new WordTag(wordProperties[i].getLemmaId(), wordProperties[i].getPartOfSpeech(), wordProperties[i].getTag());
        }
        return wordTags;
    }

    // Метод возвращает лемму из словаря
    @Override
    public Lemma getLemma(int lemmaId) {
        return DictionaryLoading.getLemmaDictionary()[lemmaId];
    }


    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        if (DictionaryLoading.loadDictionary()) {
            System.out.println("Waiting for requests...");
        }
    }
}
