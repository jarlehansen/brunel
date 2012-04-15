package ac.uk.brunel.benchmark.server.persistence;

import javax.persistence.Id;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:06 PM - 11/21/11
 */
public class Result {
    @Id
    private String timestamp;
    private String email;
    private String type;
    private String tech;
    private long timeUsed;

    public Result() {
    }

    public Result(String timestamp, String email, String type, String tech, long timeUsed) {
        this.timestamp = timestamp;
        this.email = email;
        this.type = type;
        this.tech = tech;
        this.timeUsed = timeUsed;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getTech() {
        return tech;
    }

    public long getTimeUsed() {
        return timeUsed;
    }
}
