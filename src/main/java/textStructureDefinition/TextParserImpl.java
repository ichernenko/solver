package textStructureDefinition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import wordDictionary.Dictionary;
import wordDictionary.DictionaryImpl;
import wordDictionary.WordProperty;

public class TextParserImpl implements TextParser {
    private Dictionary dictionary;

    TextParserImpl() {
        // Загружается словарь
        dictionary = new DictionaryImpl();
    }


    // Метод разбивает текст на предложения и возвращает список этих предложений с характеристиками предложений
    // Предложением является список лексем, оканчивающийся '.','!','?','¡'(?!)
    @Override
    public List<Sentence> getSentenceList(String text) {
        List<Sentence> sentenceList = new ArrayList<>();

        // удаление лишних символов из текста
        text = deleteRedundantCharacters(text);

        // разбиение текста на предложения
        int sentenceBegin = 0;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '.' || ch == '!' ||ch == '?'|| ch == '…' || ch == '¡') {
                    String sentence = text.substring(sentenceBegin, i).trim();
                    sentenceList.add(new Sentence(sentence, getWordList(sentence), ch));
                    sentenceBegin = i + 1;
                // TODO: убрать пробелы после пунктуации!
            }
        }

        return sentenceList;
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
    private List<Word> getWordList(String text) {
        List<Word> wordList = new ArrayList<>();
        int startChar = 0;
        boolean isWord = false;

        for (int i = 0; i < text.length(); i++) {
            if ((text.charAt(i) >= 'А' && text.charAt(i) <= 'п') || (text.charAt(i) >= 'р' && text.charAt(i) <= 'ё')) {
                if (!isWord) {
                    startChar = i;
                    isWord = true;
                }
            } else {
                if (isWord) {
                    addWordToWordList(wordList, text, startChar, i);
                    isWord = false;
                }
            }
        }

        if (isWord) {
            addWordToWordList(wordList, text, startChar, text.length());
        }

        return wordList;
    }

    private WordTag[] getWordTagArray(WordProperty[] wordPropertyArray) {
        if (wordPropertyArray == null)
            return new WordTag[]{};

        WordTag[] wordTagArray = new WordTag[wordPropertyArray.length];
        for (int i = 0; i < wordPropertyArray.length; i++) {
            String property = wordPropertyArray[i].getPartOfSpeech().getAllProperties();
            String morpheme = dictionary.getLemmaArray()[wordPropertyArray[i].getLemmaId() - 1].getLemma();
            String morphemicProperty = dictionary.getLemmaArray()[wordPropertyArray[i].getLemmaId() - 1].getTag();
            wordTagArray[i] = new WordTag(property, morpheme, morphemicProperty);
        }
        return wordTagArray;
    }

    // Метод добавляет слово в массив слов
    private void addWordToWordList(List<Word> wordList, String text, int start, int end) {
        String word = text.substring(start, end).toLowerCase();
        WordProperty[] wordPropertyArray = dictionary.getWordMap().get(word);
        WordTag[] wordTagArray = getWordTagArray(wordPropertyArray);
        wordList.add(new Word(word, wordTagArray));
    }

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("Waiting for requests...");
    }
}
