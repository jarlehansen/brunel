package com.googlecode.sc2dm.server.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:29 PM - 1/7/11
 */
public class GoogleAuthentication implements Authentication {
    private final Logger logger = LoggerFactory.getLogger(GoogleAuthentication.class);

    private final AuthenticationData authenticationData;

    public GoogleAuthentication(AuthenticationData authenticationData) {
        this.authenticationData = authenticationData;
    }

    public String getAuthToken() {
        String token = "";

        try {
            HttpURLConnection connection = authenticationData.getHttpConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(authenticationData.createAuthString());
            writer.close();

            StringBuilder response = new StringBuilder();
            //Get the response
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader rd = new BufferedReader(new
                        InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = rd.readLine()) != null) {
                    response.append(line);
                }

                rd.close();
            }

            if (response.length() == 0) {
                logger.warn("Unable to retrieve token, http response is null");
            } else {
                String[] parts = response.toString().split("=");
                token = parts[parts.length - 1];

                logger.info("Token received: " + token);
            }
        } catch (IOException io) {
            logger.error("Unable to connect to the c2dm authentication url", io);
            throw new IllegalStateException("Unable to connect to the c2dm authentication url");
        }

        return token;
    }
}
