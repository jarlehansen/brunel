package com.googlecode.sc2dm.annotations;

import android.app.Activity;
import com.googlecode.sc2dm.annotations.types.OnErrorObject;
import com.googlecode.sc2dm.annotations.types.OnMessageObject;
import com.googlecode.sc2dm.annotations.types.OnRegisteredObject;
import com.googlecode.sc2dm.log.SC2DMLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:36 PM - 9/18/11
 */
public enum AnnotationFinder {
    ;

    public static OnRegisteredObject findOnRegistered(Class<?> clazz) {
        Method m = findAnnotatedMethod(clazz, OnRegistered.class);

        if (m == null)
            return null;
        else
            return new OnRegisteredObject(clazz, m, getServerUri(m));
    }

    private static String getServerUri(Method method) {
        String serverUri = (method.getAnnotation(OnRegistered.class)).value();
        SC2DMLogger.i("@OnRegistered ServerUrl: ", serverUri);

        return serverUri;
    }

    public static OnMessageObject findOnMessage(Class<?> clazz) {
        Method m = findAnnotatedMethod(clazz, OnMessage.class);

        if (m == null)
            return findOnMessageNotification(clazz);
        else
            return new OnMessageObject(clazz, m);
    }

    private static OnMessageObject findOnMessageNotification(Class<?> clazz) {
        Method m = findAnnotatedMethod(clazz, OnMessageNotification.class);

        if (m == null)
            return null;
        else
            return new OnMessageObject(clazz, m, getActivityClass(m));
    }

    private static Class<? extends Activity> getActivityClass(Method method) {
        Class<? extends Activity> clazz = (method.getAnnotation(OnMessageNotification.class)).value();
        SC2DMLogger.i("@OnMessageNotification Activity class: ", clazz.getName());

        return clazz;
    }

    public static OnErrorObject findOnError(Class<?> clazz) {
        Method m = findAnnotatedMethod(clazz, OnError.class);

        if (m == null)
            return null;
        else
            return new OnErrorObject(clazz, m);
    }

    private static Method findAnnotatedMethod(Class<?> clazz, Class<? extends Annotation> annotation) {
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                SC2DMLogger.i(annotation.getSimpleName(), " found in ", clazz.getSimpleName(), ".", method.getName());

                return method;
            }
        }

        return null;
    }


}
