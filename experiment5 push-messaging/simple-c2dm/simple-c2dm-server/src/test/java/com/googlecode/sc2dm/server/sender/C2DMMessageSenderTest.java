package com.googlecode.sc2dm.server.sender;

import com.googlecode.sc2dm.server.authentication.AuthenticationData;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:47 PM - 9/28/11
 */
public class C2DMMessageSenderTest {
    private AuthenticationData authenticationData;

    @Before
    public void setUp() {
        authenticationData = new AuthenticationData("email", "password", "source");
    }

    @Test
    public void init_MessageSender() {
        MessageSender messageSender = C2DMMessageSender.createMessageSender(authenticationData);

        assertThat(messageSender, is(C2DMMessageSender.class));
    }

    @Test
    public void getStoredToken() {
        C2DMMessageSender messageSender = (C2DMMessageSender) C2DMMessageSender.createMessageSender("stored-token");

        assertEquals("stored-token", messageSender.getToken());
    }

    @Test
    public void updatedAuthToken() {
        AuthTokenUpdater authTokenUpdater = mock(AuthTokenUpdater.class);
        C2DMMessageSender messageSender = (C2DMMessageSender) C2DMMessageSender.createMessageSender("stored-token", authTokenUpdater);
        messageSender.handleUpdatedTokenResponse("updated-token");

        verify(authTokenUpdater).updateToken("updated-token");
    }

    @Test
    public void updatedAuthToken_nullValue() {
        C2DMMessageSender messageSender = (C2DMMessageSender) C2DMMessageSender.createMessageSender("stored-token", null);
        messageSender.handleUpdatedTokenResponse("updated-token");
    }
}
