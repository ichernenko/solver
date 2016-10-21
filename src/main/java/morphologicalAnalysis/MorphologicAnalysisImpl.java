package morphologicalAnalysis;

import java.util.*;

import common.MethodRange;
import common.RangeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dictionaryLoading.DictionaryLoading;
import dictionaryLoading.Lemma;
import dictionaryLoading.WordProperty;
import textAnalysis.Lexeme;
import textAnalysis.LexemeDescriptor;
import textAnalysis.TextBlock;
import common.Range;

public class MorphologicAnalysisImpl implements MorphologicAnalysis {
    private static Map<String, WordProperty[]> dictionary;
    private static int FRACTION_ELEMENTS_NUMBER = 2;
    private static int INTEGER_ELEMENTS_NUMBER = 1;
    private static int DICTIONARY_ELEMENTS_NUMBER = 1;
    private static int UNKNOWN_ELEMENTS_NUMBER = 1;

    private static MethodRange[] methodRanges = {
            new MethodRange(FRACTION_ELEMENTS_NUMBER, MorphologicAnalysisImpl::processFraction),
            new MethodRange(INTEGER_ELEMENTS_NUMBER, MorphologicAnalysisImpl::processInteger),
            new MethodRange(DICTIONARY_ELEMENTS_NUMBER, MorphologicAnalysisImpl::processDictionary),
            new MethodRange(UNKNOWN_ELEMENTS_NUMBER, MorphologicAnalysisImpl::processUnknown)
    };

    @Override
    public List<Paragraph> getParagraphs(List<TextBlock> textBlocks) {
        List<Paragraph> paragraphs = new ArrayList<>();
        textBlocks.forEach(textBlock -> {
            List<Word> words = new ArrayList<>(textBlock.getLexemes().size());
            List<Range> ranges = textBlock.getUnprocessedRanges();
            List<Lexeme> lexemes = textBlock.getLexemes();
            // Определяются слова, которые можно распознать по признаку - пунктуации
            // (значения из метода возвращаются через параметры, чтобы не городить огород
            // для этого в каждом методе должны добавляться найденные слова в список words
            // и изменяться диапазон поиска слов из расчета уже найденных

            RangeHandler rangeHandler = new RangeHandler(ranges, lexemes, words);
            rangeHandler.processElements(methodRanges);
            //rangeHandler.processElements(MorphologicAnalysisImpl::processDictionary);

            Collections.sort(words);
            paragraphs.add(new Paragraph(words, textBlock.getTextBlockPunctuation()));
        });
        return paragraphs;
    }

    // Метод находит последовательность сиволов, состоящую только из цифр
    // и создает морфологическую единицу как экземпляр класса Integer
    private static Word processInteger(List lexemes, int i) {
        Lexeme lexeme = (Lexeme) lexemes.get(i);
        LexemeDescriptor lexemeDescriptor = lexeme.getLexemeDescriptor();
        if (lexemeDescriptor.isHasDigit() && !lexemeDescriptor.isHasLetter()) {
            WordTag[] wordTags = {new WordTag(-1, "целое_число", "кол им")};
            return new Word(lexeme.getOrder(), lexeme.getLexeme(), wordTags, lexeme.getPunctuation(), INTEGER_ELEMENTS_NUMBER);
        }
        return null;
    }

    // Метод находит последовательности сиволов, состоящие только из цифр, затем следующую запятую, и снова последовательность цифр
    // и создает морфологическую единицу как экземпляр класса Integer
    private static Word processFraction(List lexemes, int i) {
        Lexeme lexeme1 = (Lexeme) lexemes.get(i);
        LexemeDescriptor lexeme1Descriptor = lexeme1.getLexemeDescriptor();
        if (lexeme1Descriptor.isHasDigit() && !lexeme1Descriptor.isHasLetter()) {
            Lexeme lexeme2 = (Lexeme) lexemes.get(i + 1);
            LexemeDescriptor lexeme2Descriptor = lexeme2.getLexemeDescriptor();
            if (lexeme2Descriptor.isHasDigit() && !lexeme2Descriptor.isHasLetter()) {
                if (",".equals(lexeme1.getPunctuation())) {
                    String lexemeString = lexeme1.getLexeme() + ',' + lexeme2.getLexeme();
                    WordTag[] wordTags = {new WordTag(-2, "дробное_число", "кол им")};
                    return new Word(lexeme1.getOrder(), lexemeString, wordTags, lexeme2.getPunctuation(), FRACTION_ELEMENTS_NUMBER);
                }
            }
        }
        return null;
    }

    private static Word processUnknown(List lexemes, int i) {
        Lexeme lexeme = (Lexeme) lexemes.get(i);
        WordTag[] wordTags = {new WordTag(-9999, "неизв", "")};
        return new Word(lexeme.getOrder(), lexeme.getLexeme(), wordTags, lexeme.getPunctuation(), UNKNOWN_ELEMENTS_NUMBER);
    }

//    private void findIndependentWordsWithPunctuation(TextBlock textBlock, List<Word> words) {
//        List<Punctuation> punctuations = textBlock.getPunctuations();
//        List<Lexeme> lexemes = textBlock.getLexemes();
//        class ddd {int i = 0;}
//        ddd fff = new ddd();
//
//        punctuations.removeIf(punctuation -> {
//            Word word = null;
//            punctuation.setOrder(punctuation.getOrder() - fff.i);
//            int punctuationOrder = punctuation.getOrder();
//
//            if (punctuation.getPunctuation().length() == 1) {
//                switch (punctuation.getPunctuation().charAt(0)) {
//                        case ',': word = findComma(textBlock, punctuationOrder); break;
////                        case '@': word = findAt(textBlock, punctuation.getOrder()); break;
////                        case '$': word = findDollar(textBlock, punctuation.getOrder()); break;
////                        case '&': word = findEt(textBlock, punctuation.getOrder()); break;
//                }
//            }
//            if (word != null) {
//                words.add(word);
//                // Быть может стоит поменять тип списка в lexemes на LinkedList,
//                // и в методы передовать ссылку на текущий элемент списка
//                lexemes.subList(punctuationOrder, punctuationOrder + word.getElementNumber()).clear();
//                fff.i++;
//                return true;
//            }
//            return false;
//        });
//    }

//    private static Word findComma(TextBlock textBlock, int lexemeOrder) {
//        return findFractions(textBlock, lexemeOrder);
//    }



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
            for (int j = 0; j < word.length(); j++, idiomTailIndex++) {
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
                    idiomTailIndex++;
                }
            }
        }
        return idiomTail.length() == idiomTailIndex;
    }

    private static Word processDictionary(List lexemes, int i) {
        Lexeme lexeme = (Lexeme) lexemes.get(i);
        String lexemeString = lexeme.getLexeme();
        WordProperty[] wordProperties = dictionary.get(lexemeString);
        if (wordProperties != null) {
            WordTag[] wordTags = createWordTags(wordProperties);
            return new Word(lexeme.getOrder(), lexemeString, wordTags, lexeme.getPunctuation(), DICTIONARY_ELEMENTS_NUMBER);
        }
        return null;
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
        Lemma[] dictionary = DictionaryLoading.getLemmaDictionary();
        paragraphs.forEach(paragraph -> {
            sb.append("<span class=\"task-solution-textBlock-font\">");
            sb.append(paragraph.getParagraph());
            sb.append("</span><table border=\"1\" class=\"task-solution-table\"><tr><th>Слово</th><th>Тег</th><th>Лемма</th><th>Тег леммы</th></tr>");
            List<Word> words = paragraph.getWords();
            words.forEach(word -> {
                WordTag[] wordTags = word.getWordTags();
                if (wordTags[0].getLemmaId() != -9999) {
                    for (WordTag wordTag : wordTags) {
                        // TODO: это временный вывод и в дальнейшем будет пересмотрен!
                        String wordTagString = wordTag.getPartOfSpeech().getAllProperties();
                        int lemmaId = wordTag.getLemmaId() - 1;

                        String lemmaWord, lemmaTag;
                        if (lemmaId >= 0) {
                            Lemma lemma = dictionary[lemmaId];
                            lemmaWord = lemma.getLemma();
                            lemmaTag = lemma.getPartOfSpeech() + ' ' + (lemma.getTag() == null ? "" : lemma.getTag());
                        } else {
                            lemmaWord = "";
                            lemmaTag = "";
                        }

                        sb.append("<tr><td>");
                        sb.append(word.getWord());
                        sb.append("</td><td>");
                        sb.append(wordTagString);
                        sb.append("</td><td>");
                        sb.append(lemmaWord);
                        sb.append("</td><td>");
                        sb.append(lemmaTag);
                        sb.append("</td></tr>");
                    }
                } else {
                    sb.append("<tr><td><font color=\"red\">");
                    sb.append(word.getWord());
                    sb.append("</font></td><td><font color=\"red\">");
                    sb.append(wordTags[0].getPartOfSpeech().getAllProperties());
                    sb.append("</font></td><td></td><td></td></tr>");
                }
            });
            sb.append("</table>");
        });
        return sb.toString();
    }


    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        if (DictionaryLoading.loadDictionary()) {
            dictionary = DictionaryLoading.getWordDictionary();
            System.out.println("Waiting for requests...");
        }
    }
}
