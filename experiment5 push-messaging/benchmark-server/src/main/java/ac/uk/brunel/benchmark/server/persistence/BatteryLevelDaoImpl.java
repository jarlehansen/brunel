package ac.uk.brunel.benchmark.server.persistence;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:06 PM - 2/25/12
 */
public class BatteryLevelDaoImpl implements BatteryLevelDao {
    private static final Logger logger = LoggerFactory.getLogger(BatteryLevelDaoImpl.class);
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM-yyyy,HH:mm:ss:SSSS");

    static {
        ObjectifyService.register(BatteryLevel.class);
    }

    @Override
    public void persist(String tech, long rawLevel, long level) {
        logger.info("Storing: tech=" + tech + ", rawLevel=" + rawLevel + ", level=" + level);

        Objectify objectify = ObjectifyService.begin();
        objectify.put(new BatteryLevel(formatter.format(new Date()), tech, rawLevel, level));
    }

    @Override
    public List<BatteryLevel> getAll() {
        Objectify objectify = ObjectifyService.begin();
        List<Key<BatteryLevel>> batteryKeys = objectify.query(BatteryLevel.class).listKeys();

        List<BatteryLevel> batteryLevels = new ArrayList<BatteryLevel>();

        for (Key<BatteryLevel> batteryKey : batteryKeys) {
            BatteryLevel batteryLevel = objectify.get(BatteryLevel.class, batteryKey.getName());
            batteryLevels.add(batteryLevel);
        }

        return batteryLevels;
    }
}
