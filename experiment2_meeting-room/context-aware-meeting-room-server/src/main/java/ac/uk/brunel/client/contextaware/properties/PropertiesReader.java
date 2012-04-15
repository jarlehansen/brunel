package ac.uk.brunel.client.contextaware.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 8, 2010
 * Time: 10:16:27 PM
 */
public enum PropertiesReader {
    SERVER_APP("server-app.properties"),
    BT_SERVICE("bluetooth-service.properties"),
    JETTY("jetty-server.properties");

    private final Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
    private final Properties prop;

    private PropertiesReader(final String fileName) {
        prop = new Properties();

        try {
            final InputStream input = this.getClass().getClassLoader().getResourceAsStream("application-config/" + fileName);
            if (input != null) {
                prop.load(input);
            }
        } catch (IOException io) {
            final String errorMsg = "Unable to load " + fileName;

            if (logger.isErrorEnabled()) {
                logger.error(errorMsg, io);
            }

            throw new IllegalStateException(errorMsg, io);
        }
    }

    public String get(final String key) {
        final String value;

        if (key != null) {
            value = prop.getProperty(key);
        } else {
            value = "";
        }

        return ((value == null) ? "" : value);
    }

    public String getCloudServiceName(final String key) {
        final String value;

        if(key != null) {
            value = get(PropertiesConstants.SERVER_CLOUD_ADDRESS) + get(key);
        } else {
            value = "";
        }

        return value;
    }
}
