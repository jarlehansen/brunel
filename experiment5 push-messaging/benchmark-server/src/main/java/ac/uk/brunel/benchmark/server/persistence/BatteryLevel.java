package ac.uk.brunel.benchmark.server.persistence;

import javax.persistence.Id;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:03 PM - 2/25/12
 */
public class BatteryLevel {
    @Id
    private String timestamp;
    private String tech;
    private long rawLevel;
    private long level;

    public BatteryLevel() {
    }

    public BatteryLevel(String timestamp, String tech, long rawLevel, long level) {
        this.timestamp = timestamp;
        this.tech = tech;
        this.rawLevel = rawLevel;
        this.level = level;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTech() {
        return tech;
    }

    public long getRawLevel() {
        return rawLevel;
    }

    public long getLevel() {
        return level;
    }
}
