package servlets;

import dictionaryLoading.Lemma;
import morphologicAnalysis.MorphologicAnalysis;
import semanticSpace.Question;
import semanticSpace.QuestionImpl;
import semanticSpace.SemanticSpace;
import semanticSpace.SemanticSpaceImpl;
import morphologicAnalysis.Sentence;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import morphologicAnalysis.Word;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SolverServlet extends HttpServlet {
    private ApplicationContext context = new ClassPathXmlApplicationContext("rmi-client-beans.xml");
    private MorphologicAnalysis morphologicAnalysis = (MorphologicAnalysis)context.getBean("morphologicAnalysisBean");

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

        List<Sentence> sentences = morphologicAnalysis.getSentences(textParameter);

        out.print("<data><solution>");
        sentences.forEach(m -> out.print(m.printSentenceWithSpaces() + "<br/>"));
        out.print("<hr/><br/>");

        sentences.forEach(m -> {
            out.print("<span class=\"task-solution-sentence-font\">" + m.printSentence() + "</span>");
            out.print("<table border=\"1\" class=\"task-solution-table\">");
            out.print("<tr><th>Слово</th><th>Тег</th><th>Лемма</th><th>Тег леммы</th></tr>");
            for (Word word: m.getWords()){
                if (word.getWordTags().length != 0) {
                    for (int i = 0; i < word.getWordTags().length; i++) {
                        // TODO: это временный вывод и в дальнейшем будет пересмотрен!
                        String wordTag = word.getWordTags()[i].getPartOfSpeech().getAllProperties();
                        int lemmaId = word.getWordTags()[i].getLemmaId() - 1;
                        Lemma lemma = morphologicAnalysis.getLemma(lemmaId);
                        String lemmaWord = lemma.getLemma();
                        String lemmaTag = lemma.getPartOfSpeech() + ' ' + (lemma.getTag() == null ? "" : lemma.getTag());

                        out.print("<tr><td>" + word.getWord() + "</td><td>" + wordTag + "</td><td>" + lemmaWord + "</td><td>" + lemmaTag + "</td></tr>");
                    }
                } else {
                    out.print("<tr><td><font color=\"red\">" + word + "</font></td><td></td><td></td><td></td></tr>");
                }
            };
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