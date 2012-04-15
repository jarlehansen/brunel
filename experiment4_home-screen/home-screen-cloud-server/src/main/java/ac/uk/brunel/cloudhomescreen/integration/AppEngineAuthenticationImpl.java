package ac.uk.brunel.cloudhomescreen.integration;

import ac.uk.brunel.cloudhomescreen.config.PropertiesLoader;
import ac.uk.brunel.cloudhomescreen.constants.HomeScreenConstants;
import ac.uk.brunel.cloudhomescreen.dto.AuthToken;
import ac.uk.brunel.cloudhomescreen.persistence.auth.AuthTokenDao;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:29 PM - 1/7/11
 */
public class AppEngineAuthenticationImpl implements AppEngineAuthentication {
    private final Logger logger = LoggerFactory.getLogger(AppEngineAuthenticationImpl.class);

    private final AuthTokenDao authTokenDao;

    private final StringBuilder pushConfiguration;
    private final URL authenticationUrl;

    @Inject
    public AppEngineAuthenticationImpl(final AuthTokenDao authTokenDao) {
        this.authTokenDao = authTokenDao;

        pushConfiguration = new StringBuilder();

        try {
            pushConfiguration.append(URLEncoder.encode("accountType", "UTF-8")).append("=");
            pushConfiguration.append(PropertiesLoader.APP.getProperty(HomeScreenConstants.PROP_C2DM_ACCOUNTTYPE)).append("&");

            pushConfiguration.append(URLEncoder.encode("Email", "UTF-8")).append("=");
            pushConfiguration.append(PropertiesLoader.APP.getProperty(HomeScreenConstants.PROP_C2DM_EMAIL)).append("&");

            pushConfiguration.append(URLEncoder.encode("Passwd", "UTF-8")).append("=");
            pushConfiguration.append(PropertiesLoader.APP.getProperty(HomeScreenConstants.PROP_C2DM_PASSWORD)).append("&");

            pushConfiguration.append(URLEncoder.encode("service", "UTF-8")).append("=");
            pushConfiguration.append(PropertiesLoader.APP.getProperty(HomeScreenConstants.PROP_C2DM_SERVICE)).append("&");

            pushConfiguration.append(URLEncoder.encode("source", "UTF-8")).append("=");
            pushConfiguration.append(PropertiesLoader.APP.getProperty(HomeScreenConstants.PROP_C2DM_SOURCE));

            authenticationUrl = new URL(PropertiesLoader.APP.getProperty(HomeScreenConstants.PROP_C2DM_AUTHENTICATION_URL));
        } catch (UnsupportedEncodingException uee) {
            logger.error("Unable to encode push configuration value", uee);
            throw new IllegalStateException("Unable to encode push configuration value");
        } catch (MalformedURLException mal) {
            logger.error("Invalid authentication URL in application.properties", mal);
            throw new IllegalStateException("Invalid authentication URL in application.properties");
        }

    }

    public void persistToken(final String updatedToken) {
        authTokenDao.persistToken(new AuthToken(updatedToken));
    }

    public String getAuthToken() {
        final AuthToken authToken = authTokenDao.getToken();
        final String token;

        if (authToken == null) {
            try {
                HttpURLConnection connection = (HttpURLConnection) authenticationUrl.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(pushConfiguration.toString());
                writer.close();

                String response = null;
                //Get the response
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    BufferedReader rd = new BufferedReader(new
                            InputStreamReader(connection.getInputStream()));
                    String line;


                    while ((line = rd.readLine()) != null) {
                        response += line;
                    }

                    rd.close();
                }

                String[] parts = response.split("=");
                token = parts[parts.length - 1];

                logger.info("Token received: " + token);
                authTokenDao.persistToken(new AuthToken(token));
            } catch (IOException io) {
                logger.error("Unable to connect to the c2dm authentication url", io);
                throw new IllegalStateException("Unable to connect to the c2dm authentication url");
            }
        } else {
            token = authToken.getToken();
        }

        return token;
    }
}
