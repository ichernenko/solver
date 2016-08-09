import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TextParserImpl implements TextParser {
    private DictionaryImpl dictionary;

    TextParserImpl() {
        // Загружается словарь
        dictionary = new DictionaryImpl();
    }

    // Метод возвращает список слов со списками тегов.
    // Список слов формируется путем разбора входящего параметра-строки - text.
    // Для каждого слова проводится поиск в загруженном словаре.
    // Поиск возвращает список всех совпадений с тегами и лемму с тегом.
    public List<Word> getWordList(String text) {
        List<Word> wordList = new ArrayList<>();
        String word;
        WordTag[] wordTagArray;
        WordProperty[] wordPropertyArray;
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
                    word = text.substring(startChar, i);
                    wordPropertyArray = dictionary.getWordMap().get(word);
                    wordTagArray = getWordTagArray(wordPropertyArray);
                    wordList.add(new Word(word, wordTagArray));
                    isWord = false;
                }
            }
        }

        if (isWord) {
            word = text.substring(startChar, text.length());
            wordPropertyArray = dictionary.getWordMap().get(word);
            wordTagArray = getWordTagArray(wordPropertyArray);
            wordList.add(new Word(word, wordTagArray));
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

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("Waiting for requests...");
    }
}
