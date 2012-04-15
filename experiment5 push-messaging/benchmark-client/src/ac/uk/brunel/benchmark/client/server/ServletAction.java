package ac.uk.brunel.benchmark.client.server;

import com.googlecode.sc2dm.log.SC2DMLogger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 9:39 PM - 11/22/11
 */
public enum ServletAction {
    SMALL_MSG("small"),
    MEDIUM_MSG("medium"),
    LARGE_MSG("large"),

    RESULT("result");

    private static final String SERVER_URL = "http://benchmark-server.appspot.com/";

    private final String type;
    private final String url;

    private ServletAction(String uri) {
        this.type = uri;
        this.url = SERVER_URL + uri;
    }

    public String type() {
        return type;
    }

    public void send(String tech, String email, String regId) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("?email=").append(email);
        sb.append("&regId=").append(regId);
        sb.append("&tech=").append(tech);

        sendRequest(sb.toString());
    }

    public void send(String tech, String email) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("?email=").append(email);
        sb.append("&tech=").append(tech);

        sendRequest(sb.toString());
    }

    public synchronized void send(String type, String tech, String email, long timeUsed) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("?email=").append(email);
        sb.append("&type=").append(type);
        sb.append("&tech=").append(tech);
        sb.append("&time=").append(timeUsed);

        sendRequest(sb.toString());
    }

    private void sendRequest(String requestUrl) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(requestUrl);

            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() != 200)
                SC2DMLogger.w("Request response: " + response.getStatusLine().getStatusCode());
        } catch (IOException io) {
            SC2DMLogger.e("Unable to contact " + url, io);
        }
    }

    public static ServletAction getType(int number) {
        ServletAction servletAction = null;
        switch (number) {
            case 1:
//                servletAction = ServletAction.SMALL_MSG;
                servletAction = ServletAction.MEDIUM_MSG;
                break;
//            case 2:
//                servletAction = ServletAction.MEDIUM_MSG;
//                break;
//            case 3:
//                servletAction = ServletAction.LARGE_MSG;
//                break;
        }

        return servletAction;
    }

    public static void sendBatteryRequest(String tech, int rawLevel, int level) {
        StringBuilder sb = new StringBuilder();
        sb.append(SERVER_URL);
        sb.append("battery?tech=").append(tech);
        sb.append("&rawLevel=").append(rawLevel);
        sb.append("&level=").append(level);
        
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(sb.toString());

            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() != 200)
                SC2DMLogger.w("Request response: " + response.getStatusLine().getStatusCode());
        } catch (IOException io) {
            SC2DMLogger.e("Unable to contact " + sb.toString(), io);
        }
    }
}
