package com.googlecode.sc2dm.server.authentication;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:14 PM - 9/28/11
 */
public class GoogleAuthenticationTest {
    private AuthenticationData authenticationData;
    private GoogleAuthentication authentication;

    @Before
    public void setUp() {
        authenticationData = new AuthenticationData("email", "password", "source");
        authentication = new GoogleAuthentication(authenticationData);
    }

    @Test
    public void getAuthToken_nullResponse() {
        String token = authentication.getAuthToken();

        assertEquals("", token);
    }

    @Test
    public void getAuthToken() throws IOException {
        HttpURLConnection con = setupTestConnection();

        AuthenticationData authenticationDataSpy = spy(authenticationData);
        when(authenticationDataSpy.getHttpConnection()).thenReturn(con);

        authentication = new GoogleAuthentication(authenticationDataSpy);
        String token = authentication.getAuthToken();

        assertEquals("test-response", token);
    }

    @Test(expected = IllegalStateException.class)
    public void getAuthToken_exceptionThrown() throws IOException {
        HttpURLConnection con = mock(HttpURLConnection.class);
        when(con.getOutputStream()).thenThrow(new IOException("Test exception"));

        AuthenticationData authenticationDataSpy = spy(authenticationData);
        when(authenticationDataSpy.getHttpConnection()).thenReturn(con);

        authentication = new GoogleAuthentication(authenticationDataSpy);
        authentication.getAuthToken();
    }


    private HttpURLConnection setupTestConnection() throws MalformedURLException {
        URL url = new URL("http://localhost");
        return new HttpURLConnection(url) {
            @Override
            public void disconnect() {
            }

            @Override
            public boolean usingProxy() {
                return false;
            }

            @Override
            public void connect() throws IOException {
            }

            @Override
            public int getResponseCode() throws IOException {
                return HttpURLConnection.HTTP_OK;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return ClassLoader.getSystemResourceAsStream("test-input.properties");
            }

            @Override
            public OutputStream getOutputStream() throws IOException {
                File file = new File(ClassLoader.getSystemResource("test-output.properties").getFile());

                return new FileOutputStream(file, false);
            }
        };
    }
}
