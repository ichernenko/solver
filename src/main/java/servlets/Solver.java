package servlets;

import morphologicAnalysis.MorphologicAnalysis;
import preliminaryTextProcessing.PreliminaryTextProcessing;
import textAnalysis.Paragraph;
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
        // По причине того, что связь с модулем morphologicAnalysis осуществляется по средствам RMI
        // а stream System.out является несериализуемым, то для вывода в stream, получаются сериализуемые данные,
        // к тому же через morphologicAnalysis не могут вызываться статические методы класса!!!
        System.out.print("<data><solution>");
        text = PreliminaryTextProcessing.getResult(text);
        System.out.print(text);
        System.out.print("<hr/><br/>");

        List<Paragraph> paragraphs = TextAnalysis.getParagraphs(text);
        System.out.print(TextAnalysis.getResult(paragraphs));
        System.out.print("<hr/><br/>");

        // Необходимо paragraphs получить заново с дополненными тегами!
        paragraphs = morphologicAnalysis.setWordTags(paragraphs);
        System.out.print(morphologicAnalysis.getResult(paragraphs));
        System.out.print("</solution><answer>");

        SemanticSpace space = new SemanticSpaceImpl(text);
        String answer = space.getAnswer(new QuestionImpl(question));
        System.out.print(answer);
        System.out.print("</answer></data>");
    }
}
