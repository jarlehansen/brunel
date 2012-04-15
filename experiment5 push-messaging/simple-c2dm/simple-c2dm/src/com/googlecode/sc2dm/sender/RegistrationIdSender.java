package com.googlecode.sc2dm.sender;

import com.googlecode.sc2dm.SC2DM;
import com.googlecode.sc2dm.log.SC2DMLogger;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:48 PM - 9/21/11
 */
public class RegistrationIdSender implements Runnable {
    private final String serverUrl;
    private final String registrationId;

    public RegistrationIdSender(String serverUrl, String registrationId) {
        this.serverUrl = serverUrl;
        this.registrationId = registrationId;
    }

    public void sendInBackground() {
        new Thread(this).start();
    }

    public void run() {
        String completeRequestUrl = buildUrl();

        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(completeRequestUrl);

            client.execute(get);
            SC2DMLogger.i("Registration id sent to: ", serverUrl);
        } catch (IOException io) {
            SC2DMLogger.e("Unable to send registration id to " + completeRequestUrl, io);
        }
    }

    /**
     * Package protected for test
     */
    String buildUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(serverUrl).append("?").append("email=").append(SC2DM.INSTANCE.email());
        sb.append("&regId=").append(registrationId);

        SC2DMLogger.i("Sending to uri: ", sb.toString());

        return sb.toString();
    }
}
