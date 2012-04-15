package com.googlecode.sc2dm.server.authentication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 1:07 PM - 9/28/11
 */
public class AuthenticationData {
    private final String email;
    private final String password;
    private final String source;

    private final URL authServiceUrl;
    private static final String accountType = "GOOGLE";
    private static final String service = "ac2dm";


    public AuthenticationData(String email, String password, String source) {
        this.email = email;
        this.password = password;
        this.source = source;

        try {
            authServiceUrl = new URL("https://www.google.com/accounts/ClientLogin");
        } catch(MalformedURLException mal) {
            throw new IllegalStateException(mal);
        }
    }

    public HttpURLConnection getHttpConnection() throws IOException {
        return (HttpURLConnection)authServiceUrl.openConnection();
    }

    public String createAuthString() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();

        sb.append(URLEncoder.encode("accountType", "UTF-8")).append("=");
        sb.append(accountType).append("&");
        sb.append(URLEncoder.encode("Email", "UTF-8")).append("=");
        sb.append(email).append("&");
        sb.append(URLEncoder.encode("Passwd", "UTF-8")).append("=");
        sb.append(password).append("&");
        sb.append(URLEncoder.encode("service", "UTF-8")).append("=");
        sb.append(service).append("&");
        sb.append(URLEncoder.encode("source", "UTF-8")).append("=");
        sb.append(source);

        return sb.toString();
    }
}