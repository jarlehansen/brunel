package com.googlecode.sc2dm.annotations;

import com.googlecode.sc2dm.annotations.types.OnErrorObject;
import com.googlecode.sc2dm.annotations.types.OnMessageObject;
import com.googlecode.sc2dm.annotations.types.OnRegisteredObject;

import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:12 PM - 10/7/11
 */
public class AnnotatedClasses {
    private final List<Class<?>> classes;

    private OnRegisteredObject onRegisteredObj = null;
    private OnMessageObject onMessageObj = null;
    private OnErrorObject onErrorObj = null;

    public AnnotatedClasses(List<Class<?>> classes) {
        this.classes = classes;
    }

    public OnRegisteredObject getOnRegisteredObj() {
        return onRegisteredObj;
    }

    public OnMessageObject getOnMessageObj() {
        return onMessageObj;
    }

    public OnErrorObject getOnErrorObj() {
        return onErrorObj;
    }

    public void createAnnotationsObjects() {
        for (Class<?> clazz : classes) {
            if (onRegisteredObj == null)
                onRegisteredObj = AnnotationFinder.findOnRegistered(clazz);

            if (onMessageObj == null)
                onMessageObj = AnnotationFinder.findOnMessage(clazz);

            if (onErrorObj == null)
                onErrorObj = AnnotationFinder.findOnError(clazz);

            if (objectsArePopulated())
                return;
        }
    }

    private boolean objectsArePopulated() {
        return (onRegisteredObj != null && onMessageObj != null && onErrorObj != null);
    }
}
