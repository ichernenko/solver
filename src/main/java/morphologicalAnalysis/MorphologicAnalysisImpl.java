package morphologicalAnalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import dictionaryLoading.WordProperty;
import textAnalysis.Lexeme;
import textAnalysis.Punctuation;
import textAnalysis.TextBlock;

public class MorphologicAnalysisImpl implements MorphologicAnalysis{

    // Метод определяет теги для слов и идиом в предложениях
    @Override
    public List<Paragraph> setWordTags(List<TextBlock> textBlocks) {
        List<Paragraph> paragraphs = new ArrayList<>();
        for (TextBlock textBlock : textBlocks) {
            // Определяются слова, которые можно распознать по признаку - пунктуации
            List<Word> words = new ArrayList<>();
            for (Punctuation punctuation: textBlock.getPunctuations()) {
                Word word = null;
                if (punctuation.getPunctuation().length() == 1) {
                    switch (punctuation.getPunctuation().charAt(0)) {
//                        case ',': word = findComma(textBlock, punctuation.getLexemeOrder()); break;
//                        case '@': word = findAt(textBlock, punctuation.getLexemeOrder()); break;
//                        case '$': word = findDollar(textBlock, punctuation.getLexemeOrder()); break;
//                        case '&': word = findEt(textBlock, punctuation.getLexemeOrder()); break;
                    }
                }
                if (word != null) {
                    words.add(word);
                }
            }
            Collections.sort(words);
            paragraphs.add(new Paragraph(words));
        }
        return paragraphs;
    }

    //TODO: сделать чтобы слова проверялись только те, которые еще неопределны!!!


    // Метод находит последовательности сиволов, состоящие только из цифр, затем следующую запятую, и снова последовательность цифр
    // и создает морфологическую единицу как экземпляр класса Integer
//    private static void findFractions(TextBlock textBlock) {
//        List<Word> words = textBlock.getWords();
//        Iterator<Punctuation> iterator = textBlock.getPunctuationMarks().iterator();
//        while (iterator.hasNext()) {
//            Punctuation punctuation = iterator.next();
//
//            if (punctuation.getPunctuation() == ',') {
//                int i = punctuation.getLexemeOrder();
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
//                    word1.setWordTags(wordTags);
//                }
//            }
//        }
//    }

    // Метод находит последовательности сиволов, состоящие только из цифр
    // и создает морфологическую единицу как экземпляр класса Integer
    private static List<Word> findIntegers(TextBlock textBlock) {
        for (Lexeme lexeme : textBlock.getLexemes()) {
            if (lexeme.getLexemeDescriptor().isHasDigit() && !lexeme.getLexemeDescriptor().isHasLetter()) {
                WordTag[] wordTags = {new WordTag(-1, "целое_число", "кол им")};
//                lexeme.setWordTags(wordTags);
            }
        }
        return null;
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


    private static void findWords(TextBlock textBlock) {
//        for (Word lexeme : textBlock.getWords()) {
//            // Если слово не является идиомой, то определяем его теги
//            if (lexeme.getWordTags() == null) {
//                WordProperty[] wordProperties = DictionaryLoading.getWordDictionary().get(lexeme.getWord());
//                WordTag[] wordTags = createWordTags(wordProperties);
//                lexeme.setWordTags(wordTags);
//            }
//        }
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
    public String getResult(List<TextBlock> textBlocks) {
        StringBuilder sb = new StringBuilder();
//        textBlocks.forEach(m -> {
//            sb.append("<span class=\"task-solution-textBlock-font\">");
//            sb.append(m.getTextBlock());
//            sb.append("</span><table border=\"1\" class=\"task-solution-table\"><tr><th>Слово</th><th>Тег</th><th>Лемма</th><th>Тег леммы</th></tr>");
//            for (Word lexeme: m.getWords()){
//                if (lexeme.getWordTags().length != 0) {
//                    for (int i = 0; i < lexeme.getWordTags().length; i++) {
//                        // TODO: это временный вывод и в дальнейшем будет пересмотрен!
//                        String wordTag = lexeme.getWordTags()[i].getPartOfSpeech().getAllProperties();
//                        int lemmaId = lexeme.getWordTags()[i].getLemmaId() - 1;
//
//                        String lemmaWord, lemmaTag;
//
//                        if (lemmaId >= 0) {
//                            Lemma lemma = DictionaryLoading.getLemmaDictionary()[lemmaId];
//                            lemmaWord = lemma.getLemma();
//                            lemmaTag = lemma.getPartOfSpeech() + ' ' + (lemma.getTag() == null ? "" : lemma.getTag());
//                        } else {
//                            lemmaWord = "";
//                            lemmaTag = "";
//                        }
//
//                        sb.append("<tr><td>");
//                        sb.append(lexeme.getWord());
//                        sb.append("</td><td>");
//                        sb.append(wordTag);
//                        sb.append("</td><td>");
//                        sb.append(lemmaWord);
//                        sb.append("</td><td>");
//                        sb.append(lemmaTag);
//                        sb.append("</td></tr>");
//                    }
//                } else {
//                    sb.append("<tr><td><font color=\"red\">");
//                    sb.append(lexeme);
//                    sb.append("</font></td><td></td><td></td><td></td></tr>");
//                }
//            }
//            sb.append("</table>");
//        });
        return sb.toString();
    }


    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

//        if (DictionaryLoading.loadDictionary()) {
            System.out.println("Waiting for requests...");
//        }
    }
}
