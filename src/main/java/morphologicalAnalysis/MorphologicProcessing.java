package morphologicalAnalysis;

import common.ProcessingStage;
import common.RangeElementProcessing;
import dictionaryLoading.DictionaryLoading;
import dictionaryLoading.Homonym;
import dictionaryLoading.IdiomProperty;
import dictionaryLoading.WordProperty;
import textAnalysis.Lexeme;
import textAnalysis.LexemeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MorphologicProcessing implements ProcessingStage {
    private static Map<String, Homonym> wordDictionary = DictionaryLoading.getWordDictionary();
    private static Map<String, IdiomProperty[]> idiomDictionary = DictionaryLoading.getIdiomDictionary();

    private static int INTEGER_LEMMA_ID = -1;
    private static int FRACTION_LEMMA_ID = -2;
    private static int UNKNOWN_LEMMA_ID = -9999;

    private static int FRACTION_ELEMENTS_NUMBER = 2;
    private static int INTEGER_ELEMENTS_NUMBER = 1;
    private static int DICTIONARY_WORD_ELEMENTS_NUMBER = 1;
    private static int DICTIONARY_IDIOM_ELEMENTS_NUMBER = 2;
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
                WordTag[] wordTags = {new WordTag(INTEGER_LEMMA_ID, "целое_число", "кол им")};
                return new Word(lexeme.getOrder(), lexeme.getLexeme(), wordTags, lexeme.getPunctuation(), INTEGER_ELEMENTS_NUMBER);
            }
        }
        return null;
    }


    // Метод находит последовательности сиволов, состоящие только из цифр, затем следующую запятую, и снова последовательность цифр
    // и создает морфологическую единицу как экземпляр класса Fraction
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
                        WordTag[] wordTags = {new WordTag(FRACTION_LEMMA_ID, "дробное_число", "кол им")};
                        return new Word(lexeme1.getOrder(), lexemeString, wordTags, lexeme2.getPunctuation(), FRACTION_ELEMENTS_NUMBER);
                    }
                }
            }
        }
        return null;
    }


    // Метод создает из ранее неопределенного слова морфологическую единицу как экземпляр класса Unknown
    Word processUnknown(int i) {
        Lexeme lexeme = lexemes.get(i);
        WordTag[] wordTags = {new WordTag(UNKNOWN_LEMMA_ID, "неизв", "")};
        return new Word(lexeme.getOrder(), lexeme.getLexeme(), wordTags, lexeme.getPunctuation(), UNKNOWN_ELEMENTS_NUMBER);
    }


    // Метод находит последовательности слов, составляющие идиому
    // и создает морфологическую единицу как экземпляр класса который представляет эта идиома
    // TODO: Не работают слова с тире!!!!
    // Пока метод находит идиому наибольшей длины со всеми возможными тегами
    // В дальнейшем это решение может быть пересмотрено!!!
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
                    List<WordTag> wordTags = new ArrayList<>();
                    for (int j = 0, idiomPropertiesLength = idiomProperties.length; j < idiomPropertiesLength; j++) {
                        IdiomProperty idiomProperty = idiomProperties[j];
                        int idiomTailLexemesNumber = idiomProperty.getIdiomTailLexemesNumber();
                        if (i + idiomTailLexemesNumber <= end) {
                            if (idiomTailLexemesNumber == 0) {
                                // Формируется лексема
                                String lexemeString = lexeme1String + (lexeme1.getPunctuation().isEmpty() ? " " : lexeme1.getPunctuation()) + lexeme2String;
                                // Формируется список тегов
                                wordTags.add(new WordTag(idiomProperty.getLemmaId(), idiomProperty.getPartOfSpeech(), idiomProperty.getTag()));
                                j++;
                                while (j < idiomPropertiesLength) {
                                    idiomProperty = idiomProperties[j];
                                    wordTags.add(new WordTag(idiomProperty.getLemmaId(), idiomProperty.getPartOfSpeech(), idiomProperty.getTag()));
                                    j++;
                                }
                                return new Word(lexeme1.getOrder(), lexemeString, wordTags.toArray(new WordTag[wordTags.size()]), lexeme2.getPunctuation(), DICTIONARY_IDIOM_ELEMENTS_NUMBER);

                            } else {
                                String idiomTail = idiomProperty.getIdiomTail();
                                int count = isIdiom(startIndex, idiomTail);
                                if (count > 0) {
                                    // Формируется лексема
                                    StringBuilder lexemeStringBuilder = new StringBuilder(lexeme1String);
                                    lexemeStringBuilder.append(lexeme1.getPunctuation().isEmpty() ? " " : lexeme1.getPunctuation());
                                    lexemeStringBuilder.append(lexeme2String);
                                    lexemeStringBuilder.append(lexeme2.getPunctuation().isEmpty() ? " " : lexeme2.getPunctuation());

                                    Lexeme lexemeN;
                                    int k = startIndex;
                                    while (k < count - 1) {
                                        lexemeN = lexemes.get(k);
                                        lexemeStringBuilder.append(lexemeN.getLexeme());
                                        lexemeStringBuilder.append(lexemeN.getPunctuation().isEmpty() ? " " : lexemeN.getPunctuation());
                                        k++;
                                    }
                                    lexemeN = lexemes.get(k);
                                    lexemeStringBuilder.append(lexemeN.getLexeme());

                                    // Формируется список тегов
                                    wordTags.add(new WordTag(idiomProperty.getLemmaId(), idiomProperty.getPartOfSpeech(), idiomProperty.getTag()));
                                    j++;
                                    while (j < idiomPropertiesLength) {
                                        idiomProperty = idiomProperties[j];
                                        if (!idiomTail.equals(idiomProperty.getIdiomTail())) {
                                            break;
                                        }
                                        wordTags.add(new WordTag(idiomProperty.getLemmaId(), idiomProperty.getPartOfSpeech(), idiomProperty.getTag()));
                                        j++;
                                    }
                                    return new Word(lexeme1.getOrder(), lexemeStringBuilder.toString(), wordTags.toArray(new WordTag[wordTags.size()]), lexemeN.getPunctuation(), count - startIndex + DICTIONARY_IDIOM_ELEMENTS_NUMBER);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    // Метод проверяет включает ли оставшаяся часть предложения хвостовую часть выбранной идиомы
    private int isIdiom(int startIndex, String idiomTail) {
        int idiomTailIndex = 0;
        int idiomTailLength = idiomTail.length();
        int i = startIndex;
        int currentLexemeLength = 0;
        while (i < end) {
            String lexeme = lexemes.get(i).getLexeme();
            int lexemeLength = lexeme.length();
            currentLexemeLength += lexemeLength;

            if (currentLexemeLength > idiomTailLength) {
                return 0;
            }

            for (int j = 0; j < lexemeLength; j++, idiomTailIndex++) {
                if (lexeme.charAt(j) != idiomTail.charAt(idiomTailIndex)) {
                    return 0;
                }
            }
            i++;
            if (idiomTail.length() == idiomTailIndex) {
                return i;
            } else {
                if (idiomTail.charAt(idiomTailIndex) != ' ') {
                    return 0;
                } else {
                    idiomTailIndex++;
                    currentLexemeLength++;                }
            }
        }
        // TODO: НЕКРАСИВО!
        // Попадаем сюда, когда есть идиома длинее чем текст!!!
        return idiomTail.length() == idiomTailIndex ? i - 1: 0;
    }

    // Метод находит слово в словаре
    // и создает морфологическую единицу как экземпляр класса, который представляет это слово
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
