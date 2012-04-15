package com.googlecode.sc2dm.messages.receiver;

import com.googlecode.sc2dm.annotations.types.OnMessageObject;
import junit.framework.TestCase;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:58 PM - 10/7/11
 */
public class AnnotationMessageReceiverTest extends TestCase {

    public void testOnMessage_nullValues() {
        AnnotationMessageReceiver receiver = new AnnotationMessageReceiver(null, null);
        receiver.onMessage(null, null);
    }
}
