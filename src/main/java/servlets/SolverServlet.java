package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;

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
        response.setContentType("text/xml");
//        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String text = request.getParameter("text");
        String question = request.getParameter("question");
        PrintStream out = new PrintStream(response.getOutputStream(), true, "UTF-8");
        System.setOut(out);

        out.print("<data>");
        Solver.solve(text, question);
        out.print("</data>");

        out.close();
        response.setStatus(200);
    }
}