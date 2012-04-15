package com.googlecode.sc2dm.factory;

import com.googlecode.sc2dm.annotations.AnnotatedClasses;
import com.googlecode.sc2dm.annotations.scanner.AnnotationScanner;
import com.googlecode.sc2dm.messages.receiver.AnnotationMessageReceiver;
import com.googlecode.sc2dm.messages.receiver.MessageReceiver;
import com.googlecode.sc2dm.messages.register.AnnotationDeviceRegistration;
import com.googlecode.sc2dm.messages.register.DeviceRegistration;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:31 PM - 10/5/11
 */
public class SC2DMFactoryImpl implements SC2DMFactory {

    public DeviceRegistration createAnnotatedDeviceRegistration(AnnotatedClasses annotatedClasses) {
        return new AnnotationDeviceRegistration(annotatedClasses.getOnRegisteredObj());
    }

    public MessageReceiver createAnnotatedMessageReceiver(AnnotatedClasses annotatedClasses) {
        return new AnnotationMessageReceiver(annotatedClasses.getOnMessageObj(), annotatedClasses.getOnErrorObj());
    }

    public AnnotationScanner createAnnotationScanner(String packageName) {
        return new AnnotationScanner(packageName);
    }
}
