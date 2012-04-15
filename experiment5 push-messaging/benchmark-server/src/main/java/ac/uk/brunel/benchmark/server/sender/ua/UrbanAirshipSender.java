package ac.uk.brunel.benchmark.server.sender.ua;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:48 PM - 11/22/11
 */
public class UrbanAirshipSender {
    private static final Logger logger = LoggerFactory.getLogger(UrbanAirshipSender.class);

    private URL url;

    public UrbanAirshipSender() {
        try {
            url = new URL("https://go.urbanairship.com/api/push/");
        } catch (MalformedURLException mal) {
            throw new IllegalStateException("Malformed url", mal);
        }
    }

    public void send(String message, String regId) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");

            String authString = "";
            byte[] authEncoded = Base64.encodeBase64(authString.getBytes());
            String authStringEncoded = new String(authEncoded);
            connection.setRequestProperty("Authorization", "Basic " + authStringEncoded);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("{\"android\": {\"alert\": \"" + message + "\"}, \"apids\": [\"" + regId + "\"]}");
            writer.close();

            int responseCode = connection.getResponseCode();
            logger.info("Response code: {}", responseCode);
        } catch (IOException io) {
            logger.error("Unable to send message", io);
        }

    }
}