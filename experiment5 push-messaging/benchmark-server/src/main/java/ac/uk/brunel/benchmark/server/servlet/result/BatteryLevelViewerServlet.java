package ac.uk.brunel.benchmark.server.servlet.result;

import ac.uk.brunel.benchmark.server.persistence.BatteryLevel;
import ac.uk.brunel.benchmark.server.persistence.BatteryLevelDao;
import com.google.inject.Provider;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:38 PM - 2/25/12
 */
@Singleton
public class BatteryLevelViewerServlet extends HttpServlet {
    private final Provider<BatteryLevelDao> batteryLevelDaoProvider;

    @Inject
    public BatteryLevelViewerServlet(Provider<BatteryLevelDao> batteryLevelDaoProvider) {
        this.batteryLevelDaoProvider = batteryLevelDaoProvider;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<BatteryLevel> batteryLevels = batteryLevelDaoProvider.get().getAll();

        PrintWriter writer = response.getWriter();
        writer.println("<html><body><p>");

        for (BatteryLevel batteryLevel : batteryLevels) {
            writer.write(batteryLevel.getTimestamp() + "," + batteryLevel.getTech() + "," + batteryLevel.getRawLevel() + "," + batteryLevel.getLevel() + "<br/>");
        }

        writer.println("</p></body></html>");
        writer.close();
    }
}
