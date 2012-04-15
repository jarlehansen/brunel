package com.googlecode.sc2dm.server.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 4:27 PM - 9/28/11
 */
public class MessageData {
    private static final Logger logger = LoggerFactory.getLogger(MessageData.class);

    private final String message;
    private final String registrationId;

    public MessageData(String message, String registrationId) {
        this.message = message;
        this.registrationId = registrationId;
    }

    public String createMessageString() {
        StringBuilder content = new StringBuilder();
        content.append("registration_id=").append(registrationId);
        content.append("&collapse_key=").append(System.currentTimeMillis()).append("&data.msg=").append(message);

        String contentString = content.toString();
        if (contentString.length() > 1024) {
            logger.warn("Total size of message ({} bytes) is larger than max size (1024 bytes), removing parts of message", contentString.length());
            contentString = contentString.substring(0, 1024);
            
            logger.warn("New message: {}", contentString);
        }

        return contentString;
    }
}
