package com.googlecode.sc2dm.factory;

import com.googlecode.sc2dm.annotations.AnnotatedClasses;
import com.googlecode.sc2dm.annotations.scanner.AnnotationScanner;
import com.googlecode.sc2dm.messages.receiver.MessageReceiver;
import com.googlecode.sc2dm.messages.register.DeviceRegistration;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:32 PM - 10/5/11
 */
public interface SC2DMFactory {
    public DeviceRegistration createAnnotatedDeviceRegistration(AnnotatedClasses annotatedClasses);

    public MessageReceiver createAnnotatedMessageReceiver(AnnotatedClasses annotatedClasses);

    public AnnotationScanner createAnnotationScanner(String packageName);
}
