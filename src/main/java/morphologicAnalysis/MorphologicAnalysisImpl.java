package morphologicAnalysis;

import java.util.List;
import dictionaryLoading.DictionaryLoading;
import dictionaryLoading.IdiomProperty;
import dictionaryLoading.Lemma;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import dictionaryLoading.WordProperty;
import textAnalysis.Sentence;
import textAnalysis.Word;

public class MorphologicAnalysisImpl implements MorphologicAnalysis{

    // Метод определяет теги для слов и идиом в предложениях
    @Override
    public List<Sentence> setWordTags(List<Sentence> sentences) {
        for (Sentence sentence: sentences) {
            findIdioms(sentence);
            findWords(sentence);
        }
        return sentences;
    }

    private static void findIdioms(Sentence sentence) {
        // Находим идиому
        // Заменяем все участвующие слова на одно
        // Добавляем теги
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        List<Word> words = sentence.getWords();
        for (int i = 0; i < words.size() - 1; i++) {
            String idiomHead = words.get(i).getWord() + ' ' + words.get(i + 1).getWord();
            IdiomProperty[] idiomProperties = DictionaryLoading.getIdiomDictionary().get(idiomHead);
            if (idiomProperties != null) {
                // ключ совпал! проверяется остальная часть идиомы
                for (int j = 0; j < idiomProperties.length; j ++) {
                    if (idiomProperties[j].getIdiomTail() == null || isIdiom(words, i + 2, idiomProperties[j].getIdiomTail())) {
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


    private static void findWords(Sentence sentence) {
        for (Word word: sentence.getWords()) {
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
    public String getResult(List<Sentence> sentences) {
        StringBuilder sb = new StringBuilder();
        sentences.forEach(m -> {
            sb.append("<span class=\"task-solution-sentence-font\">");
            sb.append(m.getSentence());
            sb.append("</span><table border=\"1\" class=\"task-solution-table\"><tr><th>Слово</th><th>Тег</th><th>Лемма</th><th>Тег леммы</th></tr>");
            for (Word word: m.getWords()){
                if (word.getWordTags().length != 0) {
                    for (int i = 0; i < word.getWordTags().length; i++) {
                        // TODO: это временный вывод и в дальнейшем будет пересмотрен!
                        String wordTag = word.getWordTags()[i].getPartOfSpeech().getAllProperties();
                        int lemmaId = word.getWordTags()[i].getLemmaId() - 1;
                        Lemma lemma = DictionaryLoading.getLemmaDictionary()[lemmaId];
                        String lemmaWord = lemma.getLemma();
                        String lemmaTag = lemma.getPartOfSpeech() + ' ' + (lemma.getTag() == null ? "" : lemma.getTag());

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
            };
            sb.append("</table><br/>");
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
