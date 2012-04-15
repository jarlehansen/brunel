package com.googlecode.sc2dm.annotations.types;

import android.content.Context;

import java.lang.reflect.Method;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:21 PM - 9/18/11
 */
public abstract class AnnotatedObject {
    private final Object[] empty_args = new Object[0];

    private final Class<?> clazz;
    private final int numberOfParameters;
    protected final Method method;

    private Object instance = null;

    public AnnotatedObject(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;

        numberOfParameters = method.getParameterTypes().length;
    }

    public Method getMethod() {
        return method;
    }

    protected Object instance() {
        if (instance == null) {
            try {
                instance = clazz.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    protected Object[] buildArgs(Context context, String msg) {
        final Object[] args;
        if (numberOfParameters == 2) {
            args = new Object[]{context, msg};
        } else if (numberOfParameters == 1) {
            args = buildSingleArgArray(context, msg);
        } else {
            args = empty_args;
        }

        return args;
    }

    private Object[] buildSingleArgArray(Context context, String msg) {
        final Object[] args;
        if (argIsContext())
            args = new Object[]{context};
        else
            args = new Object[]{msg};

        return args;
    }

    private boolean argIsContext() {
        Class<?> type = method.getParameterTypes()[0];

        return (type == Context.class);
    }
}
