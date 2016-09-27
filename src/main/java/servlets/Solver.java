package servlets;

import morphologicalAnalysis.MorphologicAnalysis;
import morphologicalAnalysis.Paragraph;
import preliminaryTextProcessing.PreliminaryTextProcessing;
import textAnalysis.TextBlock;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import semanticSpace.QuestionImpl;
import semanticSpace.SemanticSpace;
import semanticSpace.SemanticSpaceImpl;
import textAnalysis.TextAnalysis;

import java.util.List;

class Solver {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("rmi-client-beans.xml");
    private static MorphologicAnalysis morphologicAnalysis = (MorphologicAnalysis)context.getBean("morphologicAnalysisBean");

    static void solve(String text, String question) {
        long startTime;
        // По причине того, что связь с модулем morphologicalAnalysis осуществляется по средствам RMI
        // а stream System.out является несериализуемым, то для вывода в stream, получаются сериализуемые данные,
        // к тому же через morphologicalAnalysis не могут вызываться статические методы класса!!!
        System.out.print("<data><solution>");

        startTime = System.nanoTime();
        text = PreliminaryTextProcessing.getResult(text);
        printPhaseHeader("Подготовительная обработка текста", startTime);
        System.out.print(text + "<br/><br/>");

        startTime = System.nanoTime();
        List<TextBlock> textBlocks = TextAnalysis.getTextBlocks(text);
        printPhaseHeader("Текстовый анализ", startTime);
        System.out.print(TextAnalysis.getResult(textBlocks));

        List<Paragraph> paragraphs = morphologicAnalysis.getParagraphs(textBlocks);
        printPhaseHeader("Морфологический анализ", startTime);
        System.out.print(morphologicAnalysis.getResult(paragraphs));
        System.out.print("</solution><answer>");

        SemanticSpace space = new SemanticSpaceImpl(text);
        String answer = space.getAnswer(new QuestionImpl(question));
        System.out.print(answer);
        System.out.print("</answer></data>");
    }

    private static void printPhaseHeader(String phaseName, long startTime) {
        long finishTime = System.nanoTime();
        System.out.print("<hr/><span class=\"task-solution-header-font\">" + phaseName + ": " + String.format("%,5d", (finishTime - startTime) / 1000) + " мкс</span><hr/>");
    }
}
