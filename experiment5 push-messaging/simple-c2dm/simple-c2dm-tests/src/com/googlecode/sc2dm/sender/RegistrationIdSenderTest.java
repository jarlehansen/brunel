package com.googlecode.sc2dm.sender;

import com.googlecode.sc2dm.SC2DM;
import junit.framework.TestCase;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 12:10 PM - 9/22/11
 */
public class RegistrationIdSenderTest extends TestCase {
    private static final String SERVER_URL = "http://test";
    private static final String REG_ID = "device-registration-id";

    private RegistrationIdSender registrationIdSender;

    public void setUp() {
        registrationIdSender = new RegistrationIdSender(SERVER_URL, REG_ID);
        SC2DM.INSTANCE.reset();
    }

    public void testBuildUrl() {
        String url = registrationIdSender.buildUrl();

        assertEquals("http://test?email=&regId=device-registration-id", url);
    }
}
