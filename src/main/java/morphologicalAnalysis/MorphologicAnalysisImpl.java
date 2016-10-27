package morphologicalAnalysis;

import java.util.*;

import common.RangeElementProcessing;
import common.RangeHandler;
import dictionaryLoading.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import textAnalysis.Lexeme;
import textAnalysis.TextBlock;
import common.Range;

public class MorphologicAnalysisImpl implements MorphologicAnalysis {
    @Override
    public List<Paragraph> getParagraphs(List<TextBlock> textBlocks) {
        MorphologicProcessing morphologicProcessing = new MorphologicProcessing();
        List<Paragraph> paragraphs = new ArrayList<>();
        textBlocks.forEach(textBlock -> {
            List<Word> words = new ArrayList<>(textBlock.getLexemes().size());
            List<Range> ranges = textBlock.getUnprocessedRanges();
            List<Lexeme> lexemes = textBlock.getLexemes();

            morphologicProcessing.setMethods(new RangeElementProcessing[]{
                    morphologicProcessing::processFraction,
                    morphologicProcessing::processInteger,
                    morphologicProcessing::processDictionaryIdiom,
                    morphologicProcessing::processDictionaryWord,
                    morphologicProcessing::processUnknown
            });

            RangeHandler rangeHandler = new RangeHandler(ranges, lexemes, words);
            rangeHandler.processElements(morphologicProcessing);

            Collections.sort(words);
            paragraphs.add(new Paragraph(words, textBlock.getTextBlockPunctuation()));
        });
        return paragraphs;
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
            System.out.println("Waiting for requests...");
        }
    }
}
