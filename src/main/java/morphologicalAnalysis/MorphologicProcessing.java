package morphologicalAnalysis;

import common.ProcessingStage;
import common.RangeElementProcessing;
import dictionaryLoading.DictionaryLoading;
import dictionaryLoading.Homonym;
import dictionaryLoading.IdiomProperty;
import dictionaryLoading.WordProperty;
import textAnalysis.Lexeme;
import textAnalysis.LexemeDescriptor;

import java.util.List;
import java.util.Map;


public class MorphologicProcessing implements ProcessingStage {
    private static Map<String, Homonym> wordDictionary = DictionaryLoading.getWordDictionary();
    private static Map<String, IdiomProperty[]> idiomDictionary = DictionaryLoading.getIdiomDictionary();

    private static int FRACTION_ELEMENTS_NUMBER = 2;
    private static int INTEGER_ELEMENTS_NUMBER = 1;
    private static int DICTIONARY_WORD_ELEMENTS_NUMBER = 1;
    private static int UNKNOWN_ELEMENTS_NUMBER = 1;

    private RangeElementProcessing<Object, Integer>[] methods;
    private List<Lexeme> lexemes;
    private int start;
    private int end;

    // Метод находит последовательность сиволов, состоящую только из цифр
    // и создает морфологическую единицу как экземпляр класса Integer
    Word processInteger(int i) {
        if (i + INTEGER_ELEMENTS_NUMBER <= end) {
            Lexeme lexeme = lexemes.get(i);
            LexemeDescriptor lexemeDescriptor = lexeme.getLexemeDescriptor();
            if (lexemeDescriptor.isHasDigit() && !lexemeDescriptor.isHasLetter()) {
                WordTag[] wordTags = {new WordTag(-1, "целое_число", "кол им")};
                return new Word(lexeme.getOrder(), lexeme.getLexeme(), wordTags, lexeme.getPunctuation(), INTEGER_ELEMENTS_NUMBER);
            }
        }
        return null;
    }


    // Метод находит последовательности сиволов, состоящие только из цифр, затем следующую запятую, и снова последовательность цифр
    // и создает морфологическую единицу как экземпляр класса Integer
    Word processFraction(int i) {
        if (i + FRACTION_ELEMENTS_NUMBER <= end) {
            Lexeme lexeme1 = lexemes.get(i);
            LexemeDescriptor lexeme1Descriptor = lexeme1.getLexemeDescriptor();
            if (lexeme1Descriptor.isHasDigit() && !lexeme1Descriptor.isHasLetter()) {
                Lexeme lexeme2 = lexemes.get(i + 1);
                LexemeDescriptor lexeme2Descriptor = lexeme2.getLexemeDescriptor();
                if (lexeme2Descriptor.isHasDigit() && !lexeme2Descriptor.isHasLetter()) {
                    if (",".equals(lexeme1.getPunctuation())) {
                        String lexemeString = lexeme1.getLexeme() + ',' + lexeme2.getLexeme();
                        WordTag[] wordTags = {new WordTag(-2, "дробное_число", "кол им")};
                        return new Word(lexeme1.getOrder(), lexemeString, wordTags, lexeme2.getPunctuation(), FRACTION_ELEMENTS_NUMBER);
                    }
                }
            }
        }
        return null;
    }


    Word processUnknown(int i) {
        Lexeme lexeme = lexemes.get(i);
        WordTag[] wordTags = {new WordTag(-9999, "неизв", "")};
        return new Word(lexeme.getOrder(), lexeme.getLexeme(), wordTags, lexeme.getPunctuation(), UNKNOWN_ELEMENTS_NUMBER);
    }


    // Метод находит последовательности слов, составляющие идиому
    // и создает морфологическую единицу как экземпляр класса Idiom
    Word processDictionaryIdiom(int i) {
        Lexeme lexeme1 = lexemes.get(i);
        String lexeme1String = lexeme1.getLexeme();
        Homonym homonym = wordDictionary.get(lexeme1String);
        if (homonym != null) {
            int wordsNumber = homonym.getWordsNumber();
            if (wordsNumber > 0 && i + wordsNumber <= end) {
                Lexeme lexeme2 = lexemes.get(i + 1);
                String lexeme2String = lexeme2.getLexeme();
                String idiomHead = lexeme1String + ' ' + lexeme2String;

                IdiomProperty[] idiomProperties = idiomDictionary.get(idiomHead);
                if (idiomProperties != null) {
                    // ключ совпал! проверяется остальная часть идиомы
                    int startIndex = i + 2;
                    for (IdiomProperty idiomProperty : idiomProperties) {
                        String idiomTail = idiomProperty.getIdiomTail();
                        if (idiomTail == null || isIdiom(startIndex, idiomTail)) {
                            // Может быть несколько идиом с одинаковой длиной и строкой - обработать нужно все!!!
                            System.out.println("Идиома найдена: " + idiomHead + (idiomTail != null ? ' ' + idiomTail : ""));

                            // Когда метод заработает: Рассмотреть перенос из отдельного метода в тело цикла!
                            // TODO: Не работают слова с тире!!!!
                        }
                    }
                }
            }
        }
        return null;
    }

    // Метод проверяет включает ли оставшаяся часть предложения хвостовую часть выбранной идиомы
    private boolean isIdiom(int startIndex, String idiomTail) {
        int idiomTailIndex = 0;
        for (int i = startIndex; i < end; i++) {
            String lexeme = lexemes.get(i).getLexeme();
            int lexemeLength = lexeme.length();
            for (int j = 0; j < lexemeLength; j++, idiomTailIndex++) {
                if (lexeme.charAt(j) != idiomTail.charAt(idiomTailIndex)) {
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

    Word processDictionaryWord(int i) {
        Lexeme lexeme = lexemes.get(i);
        String lexemeString = lexeme.getLexeme();
        Homonym homonym = wordDictionary.get(lexemeString);
        if (homonym != null) {
            WordProperty[] wordProperties = homonym.getWordProperties();
            if (wordProperties != null) {
                WordTag[] wordTags = createWordTags(wordProperties);
                return new Word(lexeme.getOrder(), lexemeString, wordTags, lexeme.getPunctuation(), DICTIONARY_WORD_ELEMENTS_NUMBER);
            }
        }
        return null;
    }

    // Метод создает массив WordTags из массива WordProperty
    // TODO: подумать!!!
    // Если бы изначально словарь все слова создавал бы с WordTags этого б делать не следовало
    private WordTag[] createWordTags(WordProperty[] wordProperties) {
        if (wordProperties == null) {
            return new WordTag[]{};
        }
        WordTag[] wordTags = new WordTag[wordProperties.length];
        for (int i = 0; i < wordProperties.length; i++) {
            wordTags[i] = new WordTag(wordProperties[i].getLemmaId(), wordProperties[i].getPartOfSpeech(), wordProperties[i].getTag());
        }
        return wordTags;
    }

    @Override
    public void setParameters(List lexemes, int start, int end) {
        this.lexemes = lexemes;
        this.start = start;
        this.end = end;
    }

    @Override
    public RangeElementProcessing<Object, Integer>[] getMethods() {
        return methods;
    }
    public void setMethods(RangeElementProcessing<Object, Integer>[] methods) {
        this.methods = methods;
    }
}
