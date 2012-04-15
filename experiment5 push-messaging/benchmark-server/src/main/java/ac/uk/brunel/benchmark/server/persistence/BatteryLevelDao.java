package ac.uk.brunel.benchmark.server.persistence;

import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:05 PM - 2/25/12
 */
public interface BatteryLevelDao {
    public void persist(String tech, long rawLevel, long level);
    public List<BatteryLevel> getAll();
}
