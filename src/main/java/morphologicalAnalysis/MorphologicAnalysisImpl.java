package morphologicalAnalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dictionaryLoading.DictionaryLoading;
import dictionaryLoading.Lemma;
import dictionaryLoading.WordProperty;
import textAnalysis.Lexeme;
import textAnalysis.TextBlock;

public class MorphologicAnalysisImpl implements MorphologicAnalysis{

    // Метод определяет теги для слов и идиом в предложениях
    @Override
    public List<Paragraph> getParagraphs(List<TextBlock> textBlocks) {
        List<Paragraph> paragraphs = new ArrayList<>();
        for (final TextBlock textBlock : textBlocks) {

            // Определение диапазона необработанных слов
            // Четный индекс - start, нечетный - end
            List<Integer> rawWordsRange = new ArrayList<>();
            rawWordsRange.add(0);
            rawWordsRange.add(textBlock.getLexemes().size());


            List<Word> words = new ArrayList<>();
            // Определяются слова, которые можно распознать по признаку - пунктуации
            // (значения из метода возвращаются через параметры, чтобы не городить огород
            // для этого в каждом методе должны добавляться найденные слова в список words
            // и изменяться диапазон поиска слов из расчета уже найденных
//            findIndependentWordsWithPunctuation(textBlock, rawWordsRange, words);
//            findDependentWordsWithPunctuation(textBlock, rawWordsRange, words);
            findWordsWithoutPunctuation(textBlock, rawWordsRange, words);

            findWordsFromDictionary(textBlock, words);

            Collections.sort(words);
            paragraphs.add(new Paragraph(words, textBlock.getPunctuations(), rawWordsRange));
        }
        return paragraphs;
    }

    //TODO: сделать чтобы слова проверялись только те, которые еще неопределны!!!

//    private void findIndependentWordsWithPunctuation(TextBlock textBlock, List<Integer> rawWordsRange, List<Word> words) {
//        for (Punctuation punctuation: textBlock.getPunctuations()) {
//            Word word = null;
//            if (punctuation.getPunctuation().length() == 1) {
//                switch (punctuation.getPunctuation().charAt(0)) {
//                        case ',': word = findComma(textBlock, punctuation.getOrder()); break;
////                        case '@': word = findAt(textBlock, punctuation.getOrder()); break;
////                        case '$': word = findDollar(textBlock, punctuation.getOrder()); break;
////                        case '&': word = findEt(textBlock, punctuation.getOrder()); break;
//                }
//            }
//            if (word != null) {
//                words.add(word);
//            }
//        }
//    }
//
//    private void findComma(TextBlock textBlock, int lexemeOrder) {
//
//    }


    private void findWordsWithoutPunctuation(TextBlock textBlock, List<Integer> rawWordsRange, List<Word> words) {
        findIntegers(textBlock, words);
    }

    // Метод находит последовательности сиволов, состоящие только из цифр, затем следующую запятую, и снова последовательность цифр
    // и создает морфологическую единицу как экземпляр класса Integer
//    private static void findFractions(TextBlock textBlock) {
//        List<Word> words = textBlock.getWords();
//        Iterator<Punctuation> iterator = textBlock.getPunctuationMarks().iterator();
//        while (iterator.hasNext()) {
//            Punctuation punctuation = iterator.next();
//
//            if (punctuation.getPunctuation() == ',') {
//                int i = punctuation.getOrder();
//                Word word1 = words.get(i);
//                Word word2 = words.get(i + 1);
//
//                if (word1.getLexemeDescriptor().isHasDigit() && !word1.getLexemeDescriptor().isHasLetter() &&
//                        word2.getLexemeDescriptor().isHasDigit() && !word2.getLexemeDescriptor().isHasLetter()) {
//
//                    String newWord = word1.getWord() + punctuation.getPunctuation() + word2.getWord();
//                    word1.setWord(newWord);
//                    words.remove(i + 1);
//                    iterator.remove();
//                    WordTag[] wordTags = {new WordTag(-1, "дробное_число", "кол им")};
//                    word1.getParagraphs(wordTags);
//                }
//            }
//        }
//    }

    // Метод находит последовательности сиволов, состоящие только из цифр
    // и создает морфологическую единицу как экземпляр класса Integer
    private static void findIntegers(TextBlock textBlock, List<Word> words) {
        List<Lexeme> lexemes = textBlock.getLexemes();
        for (int i = 0; i < lexemes.size(); i++) {
            Lexeme lexeme = lexemes.get(i);
            if (lexeme.getLexemeDescriptor().isHasDigit() && !lexeme.getLexemeDescriptor().isHasLetter()) {
                WordTag[] wordTags = {new WordTag(-1, "целое_число", "кол им")};
                words.add(new Word(i, lexeme.getLexeme(), wordTags));
            }
        }
    }


        private static void findIdioms(TextBlock textBlock) {
//        // Находим идиому
//        // Заменяем все участвующие слова на одно
//        // Добавляем теги
//        StringBuilder sb = new StringBuilder();
//        sb.setLength(0);
//        List<Word> words = textBlock.getWords();
//        for (int i = 0; i < words.size() - 1; i++) {
//            String idiomHead = words.get(i).getWord() + ' ' + words.get(i + 1).getWord();
//            IdiomProperty[] idiomProperties = DictionaryLoading.getIdiomDictionary().get(idiomHead);
//            if (idiomProperties != null) {
//                // ключ совпал! проверяется остальная часть идиомы
//                for (IdiomProperty idiomProperty : idiomProperties) {
//                    if (idiomProperty.getIdiomTail() == null || isIdiom(words, i + 2, idiomProperty.getIdiomTail())) {
//                        System.out.println("Идиома найдена: " + idiomHead);
//                    }
//                }
//            }
//        }
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

    // TODO: Добавить диапазон обработанных лексем!!!
    private static void findWordsFromDictionary(TextBlock textBlock, List<Word> words) {
        for (int i = 0; i < textBlock.getLexemes().size(); i++) {
            // Если слово не является идиомой, то определяем его теги
            String lexeme = textBlock.getLexemes().get(i).getLexeme();
            WordProperty[] wordProperties = DictionaryLoading.getWordDictionary().get(lexeme);
            WordTag[] wordTags = createWordTags(wordProperties);
            words.add(new Word(i, lexeme, wordTags));
        }
    }

    // Метод создает массив WordTags из массива WordProperty
    // TODO: подумать!!!
    // Если бы изначально словарь все слова создавал бы с WordTags этого б делать не следовало
    private static WordTag[] createWordTags(WordProperty[] wordProperties) {
        if (wordProperties == null) {
            return new WordTag[]{};
        }

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
        paragraphs.forEach(paragraph -> {
            sb.append("<span class=\"task-solution-textBlock-font\">");
            sb.append(paragraph.getParagraph());
            sb.append("</span><table border=\"1\" class=\"task-solution-table\"><tr><th>Слово</th><th>Тег</th><th>Лемма</th><th>Тег леммы</th></tr>");
            for (Word word : paragraph.getWords()){
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
