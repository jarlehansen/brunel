package com.googlecode.sc2dm;

import android.content.Context;
import com.googlecode.sc2dm.annotations.AnnotatedClasses;
import com.googlecode.sc2dm.annotations.scanner.AnnotationScanner;
import com.googlecode.sc2dm.factory.SC2DMFactory;
import com.googlecode.sc2dm.factory.SC2DMFactoryImpl;
import com.googlecode.sc2dm.log.SC2DMLogger;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 9:01 PM - 9/13/11
 */
public class SC2DMAnnotatedMessages implements PushMessages {
    private final Context context;
    private boolean performAutoAnnotationScan = true;

    private SC2DMFactory factory = new SC2DMFactoryImpl();

    public SC2DMAnnotatedMessages(Context context, String email) {
        // Make sure nothing is added to the SC2DM object
        SC2DM.INSTANCE.reset();

        this.context = context;
        SC2DM.INSTANCE.setEmail(email);

    }

    /**
     * For tests
     */
    SC2DMAnnotatedMessages(SC2DMFactory factory, String email) {
        this.factory = factory;

        SC2DM.INSTANCE.setEmail(email);
        context = null;
    }

    public void registerAnnotatedClasses(Class<?>... classes) {
        addAnnotatedClasses(Arrays.asList(classes));
    }

    /**
     * Scans the package and all sub-packages for sc2dm annotations.
     */
    public void scanPackage(String packageName) {
        SC2DMLogger.i("Using automatic package scanning, package name: ", packageName);

        AnnotationScanner annotationScanner = factory.createAnnotationScanner(packageName);
        List<Class<?>> classes = annotationScanner.scan();

        addAnnotatedClasses(classes);
    }

    public void enable() {
        if (performAutoAnnotationScan) {
            scanPackage("");
        }

        SC2DM.INSTANCE.deviceRegistration().register(context, SC2DM.INSTANCE.email());
    }

    private void addAnnotatedClasses(List<Class<?>> classes) {
        performAutoAnnotationScan = false;

        AnnotatedClasses annotatedClasses = new AnnotatedClasses(classes);
        annotatedClasses.createAnnotationsObjects();

        SC2DM.INSTANCE.setDeviceRegistration(factory.createAnnotatedDeviceRegistration(annotatedClasses));
        SC2DM.INSTANCE.setMessageReceiver(factory.createAnnotatedMessageReceiver(annotatedClasses));
    }
}
