package com.googlecode.sc2dm;

import android.content.Context;
import android.content.Intent;
import junit.framework.TestCase;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:24 PM - 10/7/11
 */
public class SC2DMCallbackAdapterTest extends TestCase {

    public void testRegister() {
        SC2DMCallbackAdapter adapter = new SC2DMCallbackAdapter(null);

        // Should not register since context is null
        adapter.register(null, "test@test.com");
    }

    public void testOnMessage_emptyIntentMessage() {
        SC2DMCallbackAdapter adapter = new SC2DMCallbackAdapter(new SC2DMCallback() {
            @Override
            public void onRegistered(Context context, String registrationId) {
            }

            @Override
            public void onMessage(Context context, String message) {
                fail("onMessage should not be called when Intent is empty");
            }

            @Override
            public void onError(Context context, String errorMsg) {
            }
        });

        adapter.onMessage(null, new Intent());
    }

    public void testOnMessage_withIntentMessage() {
        SC2DMCallbackAdapter adapter = new SC2DMCallbackAdapter(new SC2DMCallback() {
            @Override
            public void onRegistered(Context context, String registrationId) {
            }

            @Override
            public void onMessage(Context context, String message) {
                assertEquals("test-message", message);
            }

            @Override
            public void onError(Context context, String errorMsg) {
            }
        });

        Intent intent = new Intent();
        intent.putExtra("msg", "test-message");

        adapter.onMessage(null, intent);
    }
}
