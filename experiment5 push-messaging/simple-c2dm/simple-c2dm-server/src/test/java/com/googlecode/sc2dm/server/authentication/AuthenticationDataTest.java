package com.googlecode.sc2dm.server.authentication;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:02 PM - 9/28/11
 */
public class AuthenticationDataTest {
    private AuthenticationData authenticationData;

    @Before
    public void setUp() {
        authenticationData = new AuthenticationData("email", "password", "source");
    }

    @Test
    public void createAuthString() throws UnsupportedEncodingException {
        String auth = authenticationData.createAuthString();

        assertEquals("accountType=GOOGLE&Email=email&Passwd=password&service=ac2dm&source=source", auth);
    }

}
