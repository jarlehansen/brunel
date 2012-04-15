package ac.uk.brunel.benchmark.server.sender.c2dm;

import com.googlecode.sc2dm.server.sender.AuthTokenUpdater;
import com.googlecode.sc2dm.server.sender.C2DMMessageSender;
import com.googlecode.sc2dm.server.sender.MessageData;
import com.googlecode.sc2dm.server.sender.MessageSender;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:47 PM - 11/22/11
 */
public class C2DMSender {
    private String storedToken = "";

    private AuthTokenUpdater authTokenUpdater = new AuthTokenUpdater() {
        @Override
        public void updateToken(String updatedToken) {
            storedToken = updatedToken;
        }
    };

    public void send(String token, String message, String regId) {
        if (storedToken == null) {
            storedToken = token;
        }

        MessageData messageData = new MessageData(message, regId);

        MessageSender messageSender = C2DMMessageSender.createMessageSender(storedToken, authTokenUpdater);
        messageSender.sendMessage(messageData);
    }
}
