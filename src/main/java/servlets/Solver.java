package servlets;

import morphologicAnalysis.MorphologicAnalysis;
import preliminaryTextProcessing.PreliminaryTextProcessing;
import textAnalysis.Sentence;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import semanticSpace.QuestionImpl;
import semanticSpace.SemanticSpace;
import semanticSpace.SemanticSpaceImpl;
import textAnalysis.TextAnalysis;

import java.util.List;

public class Solver {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("rmi-client-beans.xml");
    private static MorphologicAnalysis morphologicAnalysis = (MorphologicAnalysis)context.getBean("morphologicAnalysisBean");

    public static void solve(String text, String question) {
        // По причине того, что связь с модулем morphologicAnalysis осуществляется по средствам RMI
        // а stream System.out является несериализуемым, то для вывода в stream, получаются сериализуемые данные,
        // к тому же через morphologicAnalysis не могут вызываться статические методы класса!!!
        System.out.print("<data><solution>");
        text = PreliminaryTextProcessing.getResult(text);
        System.out.print(text);
        System.out.print("<hr/><br/>");

        List<Sentence> sentences = TextAnalysis.getSentences(text);
        System.out.print(TextAnalysis.getResult(sentences));
        System.out.print("<hr/><br/>");

        // Необходимо sentences получить заново с дополненными тегами!
        sentences = morphologicAnalysis.setWordTags(sentences);
        System.out.print(morphologicAnalysis.getResult(sentences));
        System.out.print("</solution><answer>");

        SemanticSpace space = new SemanticSpaceImpl(text);
        String answer = space.getAnswer(new QuestionImpl(question));
        System.out.print(answer);
        System.out.print("</answer></data>");
    }
}
