package ac.uk.brunel.cloudhomescreen.integration;

import ac.uk.brunel.cloudhomescreen.config.PropertiesLoader;
import ac.uk.brunel.cloudhomescreen.config.sources.MessageType;
import ac.uk.brunel.cloudhomescreen.constants.HomeScreenConstants;
import ac.uk.brunel.cloudhomescreen.dto.DeviceId;
import ac.uk.brunel.cloudhomescreen.dto.UserConfiguration;
import ac.uk.brunel.cloudhomescreen.persistence.device.DeviceIdRetrieverDao;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:07 PM - 1/8/11
 */
public class Cloud2DevicePushImpl implements Cloud2DevicePush {
    private final Logger logger = LoggerFactory.getLogger(Cloud2DevicePushImpl.class);

    private final AppEngineAuthentication appEngineAuthentication;
    private final DeviceIdRetrieverDao deviceIdRetrieverDao;
    private final URL c2dmSendUrl;

    @Inject
    public Cloud2DevicePushImpl(final AppEngineAuthentication appEngineAuthentication,
                                final DeviceIdRetrieverDao deviceIdRetrieverDao) {
        this.appEngineAuthentication = appEngineAuthentication;
        this.deviceIdRetrieverDao = deviceIdRetrieverDao;

        try {
            c2dmSendUrl = new URL(PropertiesLoader.APP.getProperty(HomeScreenConstants.PROP_C2DM_SEND_URL));
        } catch (MalformedURLException mal) {
            logger.error("Invalid c2dm send url in application.properties", mal);
            throw new IllegalStateException("Invalid c2dm send url in application.properties");
        }
    }

    public void pushConfiguration(final UserConfiguration userConfiguration) {
        final String token = appEngineAuthentication.getAuthToken();

        DeviceId deviceId = deviceIdRetrieverDao.getDeviceId(userConfiguration.getUser());

        if (deviceId == null) {
            logger.error("DeviceId not found in datastore, push message will not be sent");
        } else {
            logger.info("Sending configuration to: " + deviceId.getEmail());
            logger.info("App Engine Token: " + token);
            logger.info("RegistrationId: " + deviceId.getRegistrationId());

            if (deviceId != null && deviceId.getRegistrationId().length() > 0) {
                sendMessage(token, deviceId, buildMessage(userConfiguration));
            }
        }
    }

    private String buildMessage(final UserConfiguration userConfiguration) {
        StringBuilder message = new StringBuilder();
        message.append("data.stats=").append(System.currentTimeMillis()).append("&");
        message.append(userConfiguration.toMessageString());

        return message.toString();
    }

    private void sendMessage(final String token, final DeviceId deviceId, final String message) {
        try {
            HttpURLConnection conn = (HttpURLConnection) c2dmSendUrl.openConnection();

            StringBuilder content = new StringBuilder();
            content.append("registration_id=").append(deviceId.getRegistrationId());
            content.append("&collapse_key=").append(System.currentTimeMillis()).append("&").append(message);

            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(content.length()));
            conn.setRequestProperty("Authorization", "GoogleLogin auth=" + token);
            conn.setRequestProperty("", deviceId.getRegistrationId());

            logger.info("sending message: " + content.toString());

            OutputStream out = conn.getOutputStream();
            out.write(content.toString().getBytes("UTF-8"));
            out.close();

            logger.info("" + conn.getResponseCode());
            logger.info(conn.getResponseMessage());

            // Check for updated token header
            String updatedAuthToken = conn.getHeaderField("Update-Client-Auth");
            if (updatedAuthToken != null && !token.equals(updatedAuthToken)) {
                logger.warn("Got updated auth token from C2DM servers: " + updatedAuthToken);
                logger.warn("Persisting updated token, please try to push message again");
                appEngineAuthentication.persistToken(updatedAuthToken);
            }
        } catch (Exception e) {
            logger.error("Error sending c2dm message", e);
        }
    }
}
