package ac.uk.brunel.cloudhomescreen.transport.servlet;

import ac.uk.brunel.cloudhomescreen.constants.HomeScreenConstants;
import ac.uk.brunel.cloudhomescreen.persistence.statistics.StatisticsDao;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:42 PM - 1/8/11
 */
@Singleton
public class StatisticsReceiver extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(StatisticsReceiver.class);

    private Provider<StatisticsDao> statisticsDaoProvider;

    @Inject
    public StatisticsReceiver(final Provider<StatisticsDao> statisticsDaoProvider) {
        this.statisticsDaoProvider = statisticsDaoProvider;
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        final String startTime = request.getParameter(HomeScreenConstants.URL_PARAM_START_TIME);
        logger.info("Start time: " + startTime);

        if (startTime != null && startTime.length() > 0 && startTime.matches("[\\d]+")) {
            long timeUsed = System.currentTimeMillis() - Long.valueOf(startTime).longValue();
            statisticsDaoProvider.get().persistTimeUsed(Long.toString(timeUsed));
        }
    }
}
