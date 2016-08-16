package servlets;

import textStructureDefinition.TextParser;
import textStructureDefinition.Word;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SolverServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String text = request.getParameter("text");
        PrintWriter out = response.getWriter();

        ApplicationContext context = new ClassPathXmlApplicationContext("rmi-client-beans.xml");
        TextParser textParser = (TextParser)context.getBean("textParserBean");
        List<Word> wordList = textParser.getWordList(text);

        out.print("<table border=\"1\" class=task-solution-table>");
        out.print("<tr><th>Слово</th><th>Тег</th><th>Лемма</th><th>Тег леммы</th></tr>");
        wordList.forEach(k-> {
            String word = k.getWord();
            if (k.getWordTagArray().length != 0) {
                for (int i = 0; i < k.getWordTagArray().length; i++) {
                    String wordTag = k.getWordTagArray()[i].getWordTag() == null ? "" : k.getWordTagArray()[i].getWordTag();
                    String lemma = k.getWordTagArray()[i].getLemma() == null ? "" : k.getWordTagArray()[i].getLemma();
                    String lemmaTag = k.getWordTagArray()[i].getLemmaTag() == null ? "" : k.getWordTagArray()[i].getLemmaTag();

                    out.print("<tr><td>" + word + "</td><td>" + wordTag + "</td><td>" + lemma + "</td><td>" + lemmaTag + "</td></tr>");
                }
            } else {
                out.print("<tr><td><font color=\"red\">" + word + "</font></td><td></td><td></td><td></td></tr>");
            }
        });
        out.print("</table>");

        out.close();
        response.setStatus(200);
    }
}