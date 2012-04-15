package ac.uk.brunel.server.contextaware.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 15, 2010
 * Time: 1:00:18 PM
 */
public enum PropertiesReader {
    SERVER_APP("server-app.properties"),
    JPA_QUERIES("jpa-queries.properties"),
    DOC_TAGS("google-docs-tags.properties"),
    BUILD_TIMESTAMP("build-timestamp.properties");

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
}
