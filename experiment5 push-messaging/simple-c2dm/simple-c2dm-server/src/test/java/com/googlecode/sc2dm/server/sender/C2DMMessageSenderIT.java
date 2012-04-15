package com.googlecode.sc2dm.server.sender;

import com.googlecode.sc2dm.server.authentication.Authentication;
import com.googlecode.sc2dm.server.authentication.AuthenticationData;
import com.googlecode.sc2dm.server.authentication.GoogleAuthentication;
import com.googlecode.sc2dm.server.sender.C2DMMessageSender;
import com.googlecode.sc2dm.server.sender.MessageData;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:23 PM - 9/28/11
 */
public class C2DMMessageSenderIT {
    private C2DMMessageSender sender;

    @Before
    public void setUp() {
        AuthenticationData authenticationData = new AuthenticationData("", "", "source");
        Authentication authentication = new GoogleAuthentication(authenticationData);
        sender = new C2DMMessageSender(authentication);
    }

    @Test
    @Ignore
    public void send() {
        MessageData data = new MessageData("something123", "");
        sender.sendMessage(data);
    }
}
