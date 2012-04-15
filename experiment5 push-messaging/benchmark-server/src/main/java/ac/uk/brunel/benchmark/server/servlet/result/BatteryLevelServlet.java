package ac.uk.brunel.benchmark.server.servlet.result;

import ac.uk.brunel.benchmark.server.persistence.BatteryLevelDao;
import com.google.inject.Provider;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:02 PM - 2/25/12
 */
@Singleton
public class BatteryLevelServlet extends HttpServlet {
    private final Provider<BatteryLevelDao> batteryLevelDaoProvider;

    @Inject
    public BatteryLevelServlet(Provider<BatteryLevelDao> batteryLevelDaoProvider) {
        this.batteryLevelDaoProvider = batteryLevelDaoProvider;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String tech = request.getParameter("tech");
        String rawLevel = request.getParameter("rawLevel");
        String level = request.getParameter("level");

        batteryLevelDaoProvider.get().persist(tech, Integer.parseInt(rawLevel), Integer.parseInt(level));
    }
}
