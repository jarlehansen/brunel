package ac.uk.brunel.benchmark.server.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:37 PM - 11/22/11
 */
public class MessageSenderFacadeImpl implements MessageSenderFacade {
    private static final Logger logger = LoggerFactory.getLogger(MessageSenderFacadeImpl.class);

    public MessageSenderFacadeImpl() {
    }

    @Override
    public void sendMessage(PushMessage pushMessage) {
        String urlString = "http://api.xtify.com/1.2/pn/push";
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<push-notification>" +
                "<android>" +
                "<appKey></appKey>" +
                "<secret></secret>" +
                "<deviceTokens><token>" + pushMessage.getRegId() + "</token></deviceTokens>" +
                "<messageTitle></messageTitle>" +
                "<message>" + pushMessage.getMessage() + "</message>" +
                "<actionType>URL</actionType>" +
                "<url>http://www.xtify.com</url>" +
                "</android>" +
                "</push-notification>";
        String result = null;
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            logger.error("Malformed", e);
        }

        HttpURLConnection urlConn = null;
        OutputStream out = null;
        BufferedReader in = null;
        if (url != null) {
            try {
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.addRequestProperty("Content-Type", "application/xml");

                urlConn.setRequestMethod("PUT");
                urlConn.setDoOutput(true);
                urlConn.setDoInput(true);
                urlConn.connect();

                // Write content data to server
                out = urlConn.getOutputStream();
                out.write(content.getBytes());
                out.flush();

                // Check response code
                if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()), 8192);
                    StringBuffer strBuff = new StringBuffer();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        strBuff.append(inputLine);
                    }
                    result = strBuff.toString();

                    logger.info("Result: {}", result);
                }
            } catch (IOException e) {
                logger.error("Unable to send xtify message", e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException io) {
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
                if (urlConn != null) {
                    urlConn.disconnect();
                }
            }
        }
    }
}
