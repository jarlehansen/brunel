package com.googlecode.sc2dm;

import android.content.Context;
import com.googlecode.sc2dm.annotations.AnnotatedClasses;
import com.googlecode.sc2dm.annotations.scanner.AnnotationScanner;
import com.googlecode.sc2dm.factory.SC2DMFactory;
import com.googlecode.sc2dm.messages.receiver.AnnotationMessageReceiver;
import com.googlecode.sc2dm.messages.receiver.MessageReceiver;
import com.googlecode.sc2dm.messages.register.AnnotationDeviceRegistration;
import com.googlecode.sc2dm.messages.register.DeviceRegistration;
import junit.framework.TestCase;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 9:03 PM - 9/13/11
 */
public class SC2DMAnnotatedMessagesTest extends TestCase {

    public void setUp() {
        SC2DM.INSTANCE.reset();
    }

    public void testInit() {
        new SC2DMAnnotatedMessages((Context) null, "test@test.com");

        assertEquals("test@test.com", SC2DM.INSTANCE.email());
        assertNull(SC2DM.INSTANCE.deviceRegistration());
        assertNull(SC2DM.INSTANCE.messageReceiver());
    }

    public void testInit_automaticPackageScan() {
        new SC2DMAnnotatedMessages((Context) null, "test@test.com").enable();

        assertEquals("test@test.com", SC2DM.INSTANCE.email());
        assertNotNull(SC2DM.INSTANCE.deviceRegistration());
        assertNotNull(SC2DM.INSTANCE.messageReceiver());
    }

    public void testScanEmptyPackage() {
        SC2DMAnnotatedMessages pushMessages = new SC2DMAnnotatedMessages(new SC2DMFactory() {
            @Override
            public DeviceRegistration createAnnotatedDeviceRegistration(AnnotatedClasses annotatedClasses) {
                // make sure there are not populated when scanning an empty package
                assertNull(annotatedClasses.getOnErrorObj());
                assertNull(annotatedClasses.getOnMessageObj());
                assertNull(annotatedClasses.getOnRegisteredObj());

                return new AnnotationDeviceRegistration(null);
            }

            @Override
            public MessageReceiver createAnnotatedMessageReceiver(AnnotatedClasses annotatedClasses) {
                return new AnnotationMessageReceiver(null, null);
            }

            @Override
            public AnnotationScanner createAnnotationScanner(String packageName) {
                return new AnnotationScanner(packageName);
            }
        }, "test@test.com");

        pushMessages.scanPackage((this.getClass().getPackage().getName() + ".non-existing-package"));

        assertEquals("test@test.com", SC2DM.INSTANCE.email());
        assertNotNull(SC2DM.INSTANCE.messageReceiver());
        assertNotNull(SC2DM.INSTANCE.deviceRegistration());
    }

    public void testScanPackage_withAnnotatedClasses() {
        SC2DMAnnotatedMessages pushMessages = new SC2DMAnnotatedMessages(new SC2DMFactory() {
            @Override
            public DeviceRegistration createAnnotatedDeviceRegistration(AnnotatedClasses annotatedClasses) {
                // make sure the values are popualted when annotations are found
                assertNotNull(annotatedClasses.getOnErrorObj());
                assertNotNull(annotatedClasses.getOnMessageObj());
                assertNotNull(annotatedClasses.getOnRegisteredObj());

                return new AnnotationDeviceRegistration(null);
            }

            @Override
            public MessageReceiver createAnnotatedMessageReceiver(AnnotatedClasses annotatedClasses) {
                return new AnnotationMessageReceiver(null, null);
            }

            @Override
            public AnnotationScanner createAnnotationScanner(String packageName) {
                return new AnnotationScanner(packageName);
            }
        }, "test@test.com");

        pushMessages.scanPackage((this.getClass().getPackage().getName()));

        assertEquals("test@test.com", SC2DM.INSTANCE.email());
        assertNotNull(SC2DM.INSTANCE.messageReceiver());
        assertNotNull(SC2DM.INSTANCE.deviceRegistration());
    }

    public void testAutomaticAnnotationScan() {
        SC2DMAnnotatedMessages pushMessages = new SC2DMAnnotatedMessages(new SC2DMFactory() {
            @Override
            public DeviceRegistration createAnnotatedDeviceRegistration(AnnotatedClasses annotatedClasses) {
                return new AnnotationDeviceRegistration(null);
            }

            @Override
            public MessageReceiver createAnnotatedMessageReceiver(AnnotatedClasses annotatedClasses) {
                return new AnnotationMessageReceiver(null, null);
            }

            @Override
            public AnnotationScanner createAnnotationScanner(String packageName) {
                // make sure AnnotationScanner is created with empty packageName
                assertEquals("", packageName);

                return new AnnotationScanner(packageName);
            }
        }, "test@test.com");
        
        pushMessages.enable();

        assertEquals("test@test.com", SC2DM.INSTANCE.email());
        assertNotNull(SC2DM.INSTANCE.messageReceiver());
        assertNotNull(SC2DM.INSTANCE.deviceRegistration());
    }

    public void testEnable_registerEmail() {
        SC2DMAnnotatedMessages pushMessages = new SC2DMAnnotatedMessages(new SC2DMFactory() {
            @Override
            public DeviceRegistration createAnnotatedDeviceRegistration(AnnotatedClasses annotatedClasses) {
                return new AnnotationDeviceRegistration(null) {
                    @Override
                    public void register(Context context, String email) {
                        // make sure register is called with same e-mail
                        assertEquals("test@test.com", email);
                    }
                };
            }

            @Override
            public MessageReceiver createAnnotatedMessageReceiver(AnnotatedClasses annotatedClasses) {
                return new AnnotationMessageReceiver(null, null);
            }

            @Override
            public AnnotationScanner createAnnotationScanner(String packageName) {
                return new AnnotationScanner(packageName);
            }
        }, "test@test.com");
        pushMessages.enable();
    }
}
