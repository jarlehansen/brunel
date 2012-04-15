package ac.uk.brunel.cloudhomescreen.persistence.statistics;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:45 PM - 1/8/11
 */
public interface StatisticsDao {
    public void persistTimeUsed(final String timeUsed);
}
