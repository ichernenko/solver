package morphologicAnalysis;

import java.util.ArrayList;
import java.util.List;
import dictionaryLoading.DictionaryLoading;
import dictionaryLoading.Lemma;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import dictionaryLoading.WordProperty;
import preliminaryTextProcessing.PreliminaryTextProcessing;

public class MorphologicAnalysisImpl implements MorphologicAnalysis {

    // Метод разбивает текст на предложения и возвращает список этих предложений с характеристиками предложений.
    // Предложением является список лексем, оканчивающийся '.','!','?','¡'(?!)
    // Списки предложений состоят из списков слов и списков пунктуаций.
    @Override
    public List<Sentence> getSentences(String text) {
        // Выполнение неотъемлемоего предыдущего уровня, а именно
        // уровня предварительной обработки текста
        text = PreliminaryTextProcessing.processText(text);

        List<Sentence> sentences = new ArrayList<>();

        if (text != null && text.length() > 0) {
            List<Word> words = new ArrayList<>();
            List<Punctuation> punctuationMarks = new ArrayList<>();
            StringBuilder token = new StringBuilder();

            int wordNumber = -1; // До первого слова
            boolean isSentence = false;
            boolean isWord = false;

            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                if (ch == '.' || ch == '!' || ch == '?' || ch == '…' || ch == '¡') {
                    if(isWord) {
                        words.add(getWord(token.toString()));
                        isWord = false;
                    }
                    punctuationMarks.add(new Punctuation(ch, wordNumber));
                    if(isSentence) {
                        sentences.add(new Sentence(words, punctuationMarks));
                        words = new ArrayList<>();
                        punctuationMarks = new ArrayList<>();
                        token.setLength(0);
                        wordNumber = -1;
                        isSentence = false;
                    }
                } else {
                    if (ch == ',' || ch == ';' || ch == ':' || ch == '(' || ch == '{' || ch == '[' || ch == ')' || ch == '}' || ch == ']' || ch == '—' || ch == '«' || ch == '»') {
                        if(isWord) {
                            words.add(getWord(token.toString()));
                            isWord = false;
                        }
                        punctuationMarks.add(new Punctuation(ch, wordNumber));
                    } else {
                        if (ch == ' ') {
                            words.add(getWord(token.toString()));
                            isWord = false;
                        } else {
                            if (!isSentence) {
                                wordNumber = 0;
                                isWord = true;
                                isSentence = true;
                            } else {
                                if (!isWord) {
                                    token.setLength(0);
                                    wordNumber ++;
                                    isWord = true;
                                }
                            }
                            token.append(ch);
                        }
                    }
                }
            }
        }
        return sentences;
    }

    // Метод возвращает слово с тегами из словаря
    private static Word getWord(String word) {
        // Вначале проверяем не является ли последовательность слов идиомой
        WordProperty[] wordProperties = DictionaryLoading.getWordDictionary().get(word);
        WordTag[] wordTags = createWordTags(wordProperties);

        return new Word(word, wordTags);
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
    public String getSolution(List<Sentence> sentences) {
        StringBuilder sb = new StringBuilder("<solution>");
        sentences.forEach(m -> {
            sb.append(m.printSentenceWithSpaces());
            sb.append("<br/>");
        });
        sb.append("<hr/><br/>");

        sentences.forEach(m -> {
            sb.append("<span class=\"task-solution-sentence-font\">");
            sb.append(m.printSentence());
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
        sb.append("</solution>");
        return sb.toString();
    }



    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        if (DictionaryLoading.loadDictionary()) {
            System.out.println("Waiting for requests...");
        }
    }
}
