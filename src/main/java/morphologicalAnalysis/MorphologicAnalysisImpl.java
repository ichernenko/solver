package morphologicalAnalysis;

import java.util.Iterator;
import java.util.List;
import dictionaryLoading.DictionaryLoading;
import dictionaryLoading.IdiomProperty;
import dictionaryLoading.Lemma;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import dictionaryLoading.WordProperty;
import textAnalysis.Paragraph;
import textAnalysis.Punctuation;
import textAnalysis.Word;

public class MorphologicAnalysisImpl implements MorphologicAnalysis{

    // Метод определяет теги для слов и идиом в предложениях
    @Override
    public List<Paragraph> setWordTags(List<Paragraph> paragraphs) {
        for (Paragraph paragraph : paragraphs) {
            findFractions(paragraph);
//            findIntegers(paragraph);
            findWords(paragraph);
            //findIdioms(paragraph);
        }
        return paragraphs;
    }

    //TODO: сделать чтобы слова проверялись только те, которые еще неопределны!!!


    // Метод находит последовательности сиволов, состоящие только из цифр, затем следующую запятую, и снова последовательность цифр
    // и создает морфологическую единицу как экземпляр класса Integer
    private static void findFractions(Paragraph paragraph) {
        List<Word> words = paragraph.getWords();
        Iterator<Punctuation> iterator = paragraph.getPunctuationMarks().iterator();
        while (iterator.hasNext()) {
            Punctuation punctuation = iterator.next();

            if (punctuation.getPunctuationMark() == ',') {
                int i = punctuation.getWordNumber();
                Word word1 = words.get(i);
                Word word2 = words.get(i + 1);

                if (word1.getWordDescriptor().isHasDigit() && !word1.getWordDescriptor().isHasLetter() &&
                        word2.getWordDescriptor().isHasDigit() && !word2.getWordDescriptor().isHasLetter()) {

                    String newWord = word1.getWord() + punctuation.getPunctuationMark() + word2.getWord();
                    word1.setWord(newWord);
                    words.remove(i + 1);
                    iterator.remove();
                    WordTag[] wordTags = {new WordTag(-1, "дробное_число", "кол им")};
                    word1.setWordTags(wordTags);
                }
            }
        }
    }

    // Метод находит последовательности сиволов, состоящие только из цифр
    // и создает морфологическую единицу как экземпляр класса Integer
    private static void findIntegers(Paragraph paragraph) {
        for (Word word : paragraph.getWords()) {
            if (word.getWordDescriptor().isHasDigit() && !word.getWordDescriptor().isHasLetter()) {
                WordTag[] wordTags = {new WordTag(-1, "целое_число", "кол им")};
                word.setWordTags(wordTags);
            }
        }
    }


        private static void findIdioms(Paragraph paragraph) {
        // Находим идиому
        // Заменяем все участвующие слова на одно
        // Добавляем теги
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        List<Word> words = paragraph.getWords();
        for (int i = 0; i < words.size() - 1; i++) {
            String idiomHead = words.get(i).getWord() + ' ' + words.get(i + 1).getWord();
            IdiomProperty[] idiomProperties = DictionaryLoading.getIdiomDictionary().get(idiomHead);
            if (idiomProperties != null) {
                // ключ совпал! проверяется остальная часть идиомы
                for (IdiomProperty idiomProperty : idiomProperties) {
                    if (idiomProperty.getIdiomTail() == null || isIdiom(words, i + 2, idiomProperty.getIdiomTail())) {
                        System.out.println("Идиома найдена: " + idiomHead);
                    }
                }
            }
        }
    }

    // Метод проверяет включает ли оставшаяся часть предложения хвостовую часть выбранной идиомы
    private static boolean isIdiom(List<Word> words, int sentenceTailStartIndex, String idiomTail) {
        int idiomTailIndex = 0;
        for (int i = sentenceTailStartIndex; i < words.size(); i++) {
            String word = words.get(i).getWord();
            for (int j = 0; j < word.length(); j++, idiomTailIndex ++) {
                if (word.charAt(j) != idiomTail.charAt(idiomTailIndex)) {
                    return false;
                }
            }
            if (idiomTail.length() == idiomTailIndex) {
                return true;
            } else {
               if (idiomTail.charAt(idiomTailIndex) != ' ') {
                   return false;
               } else {
                   idiomTailIndex ++;
               }
            }
        }

        if (idiomTail.length() != idiomTailIndex) {
            return false;
        }

        return true;
    }


    private static void findWords(Paragraph paragraph) {
        for (Word word : paragraph.getWords()) {
            // Если слово не является идиомой, то определяем его теги
            if (word.getWordTags() == null) {
                WordProperty[] wordProperties = DictionaryLoading.getWordDictionary().get(word.getWord());
                WordTag[] wordTags = createWordTags(wordProperties);
                word.setWordTags(wordTags);
            }
        }
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

    // Метод возвращает строку, представляющую результат работы класса
    @Override
    public String getResult(List<Paragraph> paragraphs) {
        StringBuilder sb = new StringBuilder();
        paragraphs.forEach(m -> {
            sb.append("<span class=\"task-solution-paragraph-font\">");
            sb.append(m.getParagraph());
            sb.append("</span><table border=\"1\" class=\"task-solution-table\"><tr><th>Слово</th><th>Тег</th><th>Лемма</th><th>Тег леммы</th></tr>");
            for (Word word: m.getWords()){
                if (word.getWordTags().length != 0) {
                    for (int i = 0; i < word.getWordTags().length; i++) {
                        // TODO: это временный вывод и в дальнейшем будет пересмотрен!
                        String wordTag = word.getWordTags()[i].getPartOfSpeech().getAllProperties();
                        int lemmaId = word.getWordTags()[i].getLemmaId() - 1;

                        String lemmaWord, lemmaTag;

                        if (lemmaId >= 0) {
                            Lemma lemma = DictionaryLoading.getLemmaDictionary()[lemmaId];
                            lemmaWord = lemma.getLemma();
                            lemmaTag = lemma.getPartOfSpeech() + ' ' + (lemma.getTag() == null ? "" : lemma.getTag());
                        } else {
                            lemmaWord = "";
                            lemmaTag = "";
                        }

                        sb.append("<tr><td>");
                        sb.append(word.getWord());
                        sb.append("</td><td>");
                        sb.append(wordTag);
                        sb.append("</td><td>");
                        sb.append(lemmaWord);
                        sb.append("</td><td>");
                        sb.append(lemmaTag);
                        sb.append("</td></tr>");
                    }
                } else {
                    sb.append("<tr><td><font color=\"red\">");
                    sb.append(word);
                    sb.append("</font></td><td></td><td></td><td></td></tr>");
                }
            }
            sb.append("</table>");
        });
        return sb.toString();
    }


    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        if (DictionaryLoading.loadDictionary()) {
            System.out.println("Waiting for requests...");
        }
    }
}
