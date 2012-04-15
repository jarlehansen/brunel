package ac.uk.brunel.benchmark.server.servlet.result;

import ac.uk.brunel.benchmark.server.persistence.ResultDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:12 PM - 11/21/11
 */
@Singleton
public class ResultServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ResultServlet.class);
    private final Provider<ResultDao> resultDaoProvider;

    @Inject
    public ResultServlet(Provider<ResultDao> resultDaoProvider) {
        this.resultDaoProvider = resultDaoProvider;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String type = request.getParameter("type");
        String tech = request.getParameter("tech");
        String timeUsed = request.getParameter("time");

        logger.info("Persisting, email: {} - type: {} - timeUsed: {}", new Object[]{email, type, timeUsed});

        if (email != null && type != null && tech != null && timeUsed != null) {
            resultDaoProvider.get().persist(email, type, tech, timeUsed);
        }
    }
}
