package servlets;

import morphologicAnalysis.MorphologicAnalysis;
import morphologicAnalysis.Sentence;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import semanticSpace.Question;
import semanticSpace.QuestionImpl;
import semanticSpace.SemanticSpace;
import semanticSpace.SemanticSpaceImpl;

import java.util.List;

public class Solver {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("rmi-client-beans.xml");
    private static MorphologicAnalysis morphologicAnalysis = (MorphologicAnalysis)context.getBean("morphologicAnalysisBean");

    public static void solve(String textParameter, String questionParameter) {
        List<Sentence> sentences = morphologicAnalysis.getSentences(textParameter);
        // По причине того, что связь с модулем morphologicAnalysis осуществляется по средствам RMI
        // а stream System.out является несериализуемым, то для вывода в stream, получаются сериализуемые данные
        System.out.print(morphologicAnalysis.getSolution(sentences));

        SemanticSpace space = new SemanticSpaceImpl(textParameter);
        Question question = new QuestionImpl(questionParameter);
        String answer = space.getAnswer(question);
        space.print(answer);
    }
}
