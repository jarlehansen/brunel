package ac.uk.brunel.benchmark.server.servlet;

import ac.uk.brunel.benchmark.server.persistence.ResultDao;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:27 PM - 2/19/12
 */
@Singleton
public class DeleteServlet extends HttpServlet {
    private Provider<ResultDao> resultDaoProvider;

    @Inject
    public DeleteServlet(Provider<ResultDao> resultDaoProvider) {
        this.resultDaoProvider = resultDaoProvider;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        resultDaoProvider.get().deleteAll();
    }
}
