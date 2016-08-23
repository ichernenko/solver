package morphologicAnalysis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tokenization.Dictionary;
import tokenization.DictionaryImpl;
import tokenization.WordProperty;

public class MorphologicAnalysisImpl implements MorphologicAnalysis {
    private Dictionary dictionary = DictionaryImpl.getInstance();

    MorphologicAnalysisImpl() {
    }

    // Метод разбивает текст на предложения и возвращает список этих предложений с характеристиками предложений
    // Предложением является список лексем, оканчивающийся '.','!','?','¡'(?!)
    @Override
    public List<Sentence> getSentences(String text) {
        List<Sentence> sentences = new ArrayList<>();

        // разбиение текста на предложения
        int sentenceBegin = 0;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '.' || ch == '!' ||ch == '?'|| ch == '…' || ch == '¡') {
                    String sentence = text.substring(sentenceBegin, i).trim();
                    sentences.add(new Sentence(sentence, getWords(sentence), ch));
                    sentenceBegin = i + 1;
                // TODO: убрать пробелы после пунктуации!
            }
        }

        return sentences;
    }


    // Метод возвращает список слов со списками тегов.
    // Список слов формируется путем разбора входящего параметра-строки - text.
    // Для каждого слова проводится поиск в загруженном словаре.
    // Поиск возвращает список всех совпадений с тегами и лемму с тегом.
    private List<Word> getWords(String text) {
        List<Word> words = new ArrayList<>();
        int beginChar = 0;
        boolean isWord = false;

        for (int i = 0; i < text.length(); i++) {
            if ((text.charAt(i) >= 'А' && text.charAt(i) <= 'п') || (text.charAt(i) >= 'р' && text.charAt(i) <= 'ё')) {
                if (!isWord) {
                    beginChar = i;
                    isWord = true;
                }
            } else {
                if (isWord) {
                    addWordToWords(words, text, beginChar, i);
                    isWord = false;
                }
            }
        }

        if (isWord) {
            addWordToWords(words, text, beginChar, text.length());
        }

        return words;
    }

    private WordTag[] getWordTags(WordProperty[] wordProperties) {
        if (wordProperties == null)
            return new WordTag[]{};

        WordTag[] wordTags = new WordTag[wordProperties.length];
        for (int i = 0; i < wordProperties.length; i++) {
            String property = wordProperties[i].getPartOfSpeech().getAllProperties();
            String lemma = dictionary.getLemmas()[wordProperties[i].getLemmaId() - 1].getLemma();
            String partOfSpeech = dictionary.getLemmas()[wordProperties[i].getLemmaId() - 1].getPartOfSpeech();
            String tag = dictionary.getLemmas()[wordProperties[i].getLemmaId() - 1].getTag();
            wordTags[i] = new WordTag(property, lemma, partOfSpeech + (tag == null ? "" : " " + tag));
        }
        return wordTags;
    }

    // Метод добавляет слово в массив слов
    private void addWordToWords(List<Word> words, String text, int begin, int end) {
        String word = text.substring(begin, end).toLowerCase();
        WordProperty[] wordProperties = dictionary.getWordMap().get(word);
        WordTag[] wordTags = getWordTags(wordProperties);
        words.add(new Word(word, wordTags));
    }

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("Waiting for requests...");
    }
}
