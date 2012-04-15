package com.googlecode.sc2dm.server.authentication;

import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 1:52 PM - 9/28/11
 */
public class GoogleAuthenticationIT {

    @Test
    @Ignore
    public void getAuthToken_validInput() {
        String email = JOptionPane.showInputDialog("Enter email");
        String password = JOptionPane.showInputDialog("Enter password");

        AuthenticationData authenticationData = new AuthenticationData(email, password, "GoogleAuthenticationIT");
        Authentication authentication = new GoogleAuthentication(authenticationData);
        String token = authentication.getAuthToken();

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void getAuthToken_invalidInput() {
        AuthenticationData authenticationData = new AuthenticationData("not-valid@gmail.com", "", "simple-c2dm-server");
        Authentication authentication = new GoogleAuthentication(authenticationData);

        String token = authentication.getAuthToken();

        assertEquals("", token);
    }
}
