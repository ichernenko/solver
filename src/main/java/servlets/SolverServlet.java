package servlets;

import semanticSpace.Question;
import semanticSpace.QuestionImpl;
import semanticSpace.SemanticSpace;
import semanticSpace.SemanticSpaceImpl;
import textStructureDefinition.Sentence;
import textStructureDefinition.TextParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import textStructureDefinition.Word;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SolverServlet extends HttpServlet {
    private ApplicationContext context = new ClassPathXmlApplicationContext("rmi-client-beans.xml");
    private TextParser textParser = (TextParser)context.getBean("textParserBean");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String textParameter = request.getParameter("text");
        String questionParameter = request.getParameter("question");
        PrintWriter out = response.getWriter();

        List<Sentence> sentences = textParser.getSentences(textParameter);

        out.print("<data><solution>");
        sentences.forEach(m -> out.print(m.getSentence() + m.getSentenceEnd() + "<br/>"));
        out.print("<hr/><br/>");

        sentences.forEach(m -> {
            List<Word> words = m.getWords();
            out.print("<span class=\"task-solution-sentence-font\">" + m.getSentence() + m.getSentenceEnd() + "</span>");
            out.print("<table border=\"1\" class=\"task-solution-table\">");
            out.print("<tr><th>Слово</th><th>Тег</th><th>Лемма</th><th>Тег леммы</th></tr>");
            words.forEach(k-> {
                String word = k.getWord();
                if (k.getWordTags().length != 0) {
                    for (int i = 0; i < k.getWordTags().length; i++) {
                        String wordTag = k.getWordTags()[i].getWordTag() == null ? "" : k.getWordTags()[i].getWordTag();
                        String lemma = k.getWordTags()[i].getLemma() == null ? "" : k.getWordTags()[i].getLemma();
                        String lemmaTag = k.getWordTags()[i].getLemmaTag() == null ? "" : k.getWordTags()[i].getLemmaTag();

                        out.print("<tr><td>" + word + "</td><td>" + wordTag + "</td><td>" + lemma + "</td><td>" + lemmaTag + "</td></tr>");
                    }
                } else {
                    out.print("<tr><td><font color=\"red\">" + word + "</font></td><td></td><td></td><td></td></tr>");
                }
            });
            out.print("</table><br/>");
        });
        out.print("</solution>");

        // Размещение объектов на семантическом пространстве
        SemanticSpace space = new SemanticSpaceImpl(/* передача параметров с уровня семантического анализа */);
        Question question = new QuestionImpl(questionParameter);
        String answer = space.getAnswer(question);

        out.print("<answer>" + answer + "</answer></data>");

        out.close();
        response.setStatus(200);
    }
}