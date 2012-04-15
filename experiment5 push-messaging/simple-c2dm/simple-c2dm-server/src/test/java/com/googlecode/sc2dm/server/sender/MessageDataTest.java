package com.googlecode.sc2dm.server.sender;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:44 PM - 9/28/11
 */
public class MessageDataTest {

    @Test
    public void createMessageString() {
        MessageData message = new MessageData("message", "registrationId");
        String msg = message.createMessageString();

        assertTrue(msg.contains("registration_id=registrationId"));
        assertTrue(msg.contains("collapse_key"));
        assertTrue(msg.contains("data.msg=message"));
    }

    @Test
    public void createMessageString_messageTooLarge() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("i");
        }

        MessageData message = new MessageData(sb.toString(), "registrationId");
        String msg = message.createMessageString();

        assertEquals(1024, msg.length());
    }
}
