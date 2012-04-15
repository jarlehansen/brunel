package ac.uk.brunel.benchmark.server.sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:15 PM - 11/27/11
 */
public class Tester {
    public static void main(String args[]) throws IOException {
        String urlString = "http://api.xtify.com/1.2/pn/push";
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<push-notification>" +
                "<android>" +
                "<appKey>f850dc86-5d9d-4f9e-a6db-f7dd2ad30334</appKey>" +
                "<secret></secret>" +
                "<deviceTokens><token></token></deviceTokens>" +
                "<messageTitle>Sample Title</messageTitle>" +
                "<message>Sample Message</message>" +
                "<actionType>URL</actionType>" +
                "<url>http://www.xtify.com</url>" +
                "</android>" +
                "</push-notification>";
        String result = null;
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
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

                    System.err.println(result);

                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (urlConn != null) {
                    urlConn.disconnect();
                }
            }
        }
    }
}
