package ac.uk.brunel.cloudhomescreen.persistence.statistics;

import ac.uk.brunel.cloudhomescreen.dto.Stats;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:45 PM - 1/8/11
 */
public class StatisticsDaoImpl implements StatisticsDao {
    private final Logger logger = LoggerFactory.getLogger(StatisticsDaoImpl.class);

    static {
        ObjectifyService.register(Stats.class);
    }

    public StatisticsDaoImpl() {
    }

    public void persistTimeUsed(final String timeUsed) {
        logger.info("Persisting time used: " + timeUsed);

        final Objectify objectify = ObjectifyService.begin();
        objectify.put(new Stats(timeUsed));
    }
}
