package ac.uk.brunel.cloudhomescreen.dto;

import ac.uk.brunel.cloudhomescreen.config.sources.MessageType;

import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:41 PM - 1/9/11
 */
public class Payload {
    private final long startTime;

    private final int key;
    private final int status;
    private final String message;

    public Payload() {
        key = -1;
        startTime = -1L;
        status = -1;
        message = "";
    }

    public  Payload(final long startTime, final int key, final int status, final String message) {
        this.startTime = startTime;
        this.key = key;
        this.status = status;
        this.message = message;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getKey() {
        return key;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String toMessageString() {
        StringBuilder sb = new StringBuilder();
        sb.append("&data.type=").append(MessageType.PUSH_MESSAGE.getType()).append("&");
        sb.append("data.key=").append(key).append("&");
        sb.append("data.status=").append(status).append("&");
        sb.append("data.message=").append(message);

        return sb.toString();
    }

     public String toUrlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type=").append(MessageType.PUSH_MESSAGE.getType()).append("&");
        sb.append("key=").append(key).append("&");
        sb.append("status=").append(status).append("&");
        sb.append("message=").append(message);

        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Payload");
        sb.append("{startTime=").append(startTime);
        sb.append(", key=").append(key);
        sb.append(", status=").append(status);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
