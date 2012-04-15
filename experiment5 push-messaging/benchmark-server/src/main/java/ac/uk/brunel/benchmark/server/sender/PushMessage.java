package ac.uk.brunel.benchmark.server.sender;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:50 PM - 11/23/11
 */
public class PushMessage {
    private final String tech;
    private final String email;
    private final String message;
    private final String regId;

    public PushMessage(String tech, String email, String message, String regId) {
        this.tech = tech;
        this.email = email;
        this.message = message;
        this.regId = regId;
    }

    public String getTech() {
        return tech;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public String getRegId() {
        return regId;
    }
}
