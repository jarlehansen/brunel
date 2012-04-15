package com.googlecode.sc2dm.messages.register;

import junit.framework.TestCase;

import java.io.IOException;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:01 PM - 10/7/11
 */
public class AnnotationDeviceRegistrationTest extends TestCase {

    public void testRegister_nullContext() {
        AnnotationDeviceRegistration registration = new AnnotationDeviceRegistration(null);
        registration.register(null, null);
    }

    public void testOnRegisteredNullValues() throws IOException {
        AnnotationDeviceRegistration registration = new AnnotationDeviceRegistration(null);
        registration.onRegistered(null, null);
    }
}
