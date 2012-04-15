package com.googlecode.sc2dm.server.sender;

import com.googlecode.sc2dm.server.authentication.Authentication;
import com.googlecode.sc2dm.server.authentication.AuthenticationData;
import com.googlecode.sc2dm.server.authentication.GoogleAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:09 PM - 9/28/11
 */
public class C2DMMessageSender implements MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(C2DMMessageSender.class);
    private static final String c2dmUrl = "http://android.apis.google.com/c2dm/send";


    private static final URL MSG_SERVICE_URL;
    private static final String HEADER_FIELD_UPDATED_TOKEN = "Update-Client-Auth";

    private Authentication authentication = null;
    private String storedToken = null;
    private AuthTokenUpdater authTokenUpdater = null;

    static {
        try {
            MSG_SERVICE_URL = new URL(c2dmUrl);
        } catch (MalformedURLException mal) {
            throw new IllegalStateException(mal);
        }
    }

    public static MessageSender createMessageSender(AuthenticationData authenticationData) {
        return new C2DMMessageSender(new GoogleAuthentication(authenticationData));
    }

    public static MessageSender createMessageSender(AuthenticationData authenticationData, AuthTokenUpdater authTokenUpdater) {
        return new C2DMMessageSender(new GoogleAuthentication(authenticationData), authTokenUpdater);
    }

    public static MessageSender createMessageSender(String token) {
        return new C2DMMessageSender(token);
    }

    public static MessageSender createMessageSender(String token, AuthTokenUpdater authTokenUpdater) {
        return new C2DMMessageSender(token, authTokenUpdater);
    }


    public C2DMMessageSender(String storedToken) {
        this.storedToken = storedToken;
    }

    public C2DMMessageSender(String storedToken, AuthTokenUpdater authTokenUpdater) {
        this.storedToken = storedToken;
        this.authTokenUpdater = authTokenUpdater;
    }

    public C2DMMessageSender(Authentication authentication) {
        this.authentication = authentication;
    }

    public C2DMMessageSender(Authentication authentication, AuthTokenUpdater authTokenUpdater) {
        this.authentication = authentication;
        this.authTokenUpdater = authTokenUpdater;
    }

    public boolean sendMessage(MessageData... messages) {
        boolean msgSent;

        try {
            for (MessageData message : messages) {
                String content = message.createMessageString();
                logger.info("Sending: " + content);

                HttpURLConnection connection = (HttpURLConnection) MSG_SERVICE_URL.openConnection();
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                connection.setRequestProperty("Content-Length", Integer.toString(content.length()));
                connection.setRequestProperty("Authorization", "GoogleLogin auth=" + getToken());

                OutputStream out = connection.getOutputStream();
                out.write(content.getBytes("UTF-8"));
                out.close();

                logger.info("Response: " + connection.getResponseCode());
                
                handleUpdatedTokenResponse(connection.getHeaderField(HEADER_FIELD_UPDATED_TOKEN));
            }

            msgSent = true;
        } catch (IOException e) {
            e.printStackTrace();
            msgSent = false;
        }

        return msgSent;
    }

    String getToken() {
        final String authToken;
        if (storedToken == null)
            authToken = authentication.getAuthToken();
        else
            authToken = storedToken;

        return authToken;
    }

    void handleUpdatedTokenResponse(String updatedAuthToken) {
        if (updatedAuthToken != null && storedToken != null && !storedToken.equals(updatedAuthToken)) {
            logger.warn("Received updated auth storedToken from C2DM servers: " + updatedAuthToken);
            storedToken = updatedAuthToken;

            if (authTokenUpdater != null)
                authTokenUpdater.updateToken(updatedAuthToken);
        }
    }
}
