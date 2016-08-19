package textStructureDefinition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import wordDictionary.Dictionary;
import wordDictionary.DictionaryImpl;
import wordDictionary.WordProperty;

public class TextParserImpl implements TextParser {
    private Dictionary dictionary = DictionaryImpl.getInstance();

    TextParserImpl() {
    }

    // Метод разбивает текст на предложения и возвращает список этих предложений с характеристиками предложений
    // Предложением является список лексем, оканчивающийся '.','!','?','¡'(?!)
    @Override
    public List<Sentence> getSentences(String text) {
        List<Sentence> sentences = new ArrayList<>();

        // удаление лишних символов из текста
        text = deleteRedundantCharacters(text);

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

    // Метод удаляет из текста лишние символы
    private String deleteRedundantCharacters(String text) {
        return  text.replaceAll("^\\s+|\\s+$","")
                    .replaceAll("\\s+"," ")
                    .replaceAll(" (,|;|\\.|!|\\?|:|\\)|\\}|\\]|%)","$1")
                    .replaceAll("(\\(|\\{|\\[|№) ","$1")
                    .replaceAll("\\.{3,}","…")
                    .replaceAll("(\\.|,|;|!|\\?|:)\\1+","$1")
                    .replaceAll("(!|\\?){2,}","¡")
                    .replaceAll(" \\((\\?|\\!)\\)","")
                    // удаление пробелов в числах
                    .replaceAll("([0-9]) ([0-9])*?","$1$2")
                    // pL - буквы русского алфавита
                    .replaceAll("(,|;|\\.|!|\\?|:|\\)|\\}|\\]|¡)([0-9]|\\pL)", "$1 $2");
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
