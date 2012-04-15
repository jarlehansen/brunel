package com.googlecode.sc2dm.annotations.types;

import android.content.Context;
import com.googlecode.sc2dm.log.SC2DMLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:07 PM - 9/20/11
 */
public class OnErrorObject extends AnnotatedObject {
    public OnErrorObject(Class<?> clazz, Method method) {
        super(clazz, method);
    }

    public void invokeOnError(Context context, String errorMsg) {
        Object instance = super.instance();

        try {
            method.invoke(instance, buildArgs(context, errorMsg));
        } catch (IllegalAccessException e) {
            SC2DMLogger.e("Unable to access onError", e);
        } catch (InvocationTargetException e) {
            SC2DMLogger.e("Unable to invoke onError", e);
        }
    }
}
