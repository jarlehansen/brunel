package ac.uk.brunel.benchmark.server.servlet.result;

import ac.uk.brunel.benchmark.server.persistence.Result;
import ac.uk.brunel.benchmark.server.persistence.ResultDao;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:56 AM - 11/27/11
 */
@Singleton
public class ResultViewerServlet extends HttpServlet {
    private final Provider<ResultDao> resultDaoProvider;

    @Inject
    public ResultViewerServlet(Provider<ResultDao> resultDaoProvider) {
        this.resultDaoProvider = resultDaoProvider;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");

        List<Result> results = new ArrayList<Result>();
        if (email != null && email.equals("all")) {
            results = resultDaoProvider.get().getAll();
        } else if (!"".equals(email)) {
            results = resultDaoProvider.get().getResultsForEmail(email);
        }


        PrintWriter writer = response.getWriter();
        writer.println("<html><body><p>");

        for (Result result : results) {
            writer.write(result.getTimestamp() + "," + result.getEmail() + "," + result.getType() + "," + result.getTech() + "," + result.getTimeUsed() + "<br/>");
        }

        writer.println("</p></body></html>");
        writer.close();
    }
}
