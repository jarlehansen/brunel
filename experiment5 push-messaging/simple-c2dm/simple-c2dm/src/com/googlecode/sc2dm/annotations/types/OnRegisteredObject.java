package com.googlecode.sc2dm.annotations.types;

import android.content.Context;
import com.googlecode.sc2dm.log.SC2DMLogger;
import com.googlecode.sc2dm.sender.RegistrationIdSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:31 PM - 9/18/11
 */
public class OnRegisteredObject extends AnnotatedObject {
    private final String serverUrl;

    public OnRegisteredObject(Class<?> clazz, Method method, String serverUrl) {
        super(clazz, method);
        this.serverUrl = serverUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void invokeOnRegistered(Context context, String registrationId) {
        if(serverUrl == null || "".equals(serverUrl))
            SC2DMLogger.i("Server url is not set, will not try to send registration id");
        else
            new RegistrationIdSender(serverUrl, registrationId).sendInBackground();

        Object instance = super.instance();

        try {
            method.invoke(instance, buildArgs(context, registrationId));
        } catch (IllegalAccessException e) {
            SC2DMLogger.e("Unable to access onRegistered", e);
        } catch (InvocationTargetException e) {
            SC2DMLogger.e("Unable to invoke onRegistered", e);
        }
    }
}
