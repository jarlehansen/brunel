package com.googlecode.sc2dm;

import android.content.Context;
import junit.framework.TestCase;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 11:54 AM - 10/6/11
 */
public class SC2DMDirectMessagesTest extends TestCase {

    public void setUp() {
        SC2DM.INSTANCE.reset();
    }

    public void testInit() {
        new SC2DMDirectMessages(null, "test@test.com", new SC2DMCallback() {
            @Override
            public void onRegistered(Context context, String registrationId) {
            }

            @Override
            public void onMessage(Context context, String message) {
            }

            @Override
            public void onError(Context context, String errorMsg) {
            }
        });

        assertEquals("test@test.com", SC2DM.INSTANCE.email());
        assertNotNull(SC2DM.INSTANCE.messageReceiver());
        assertNotNull(SC2DM.INSTANCE.deviceRegistration());
    }

}
