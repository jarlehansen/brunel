package com.googlecode.sc2dm.annotation.processor.permissions;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:00 PM - 11/20/11
 */
public enum UsesPermission {
    C2D_MESSAGE("com.googlecode.sc2dm.activity.permission.C2D_MESSAGE"),
    RECEIVE("com.google.android.c2dm.permission.RECEIVE"),
    INTERNET("android.permission.INTERNET"),
    WAKE_LOCK("android.permission.WAKE_LOCK");

    private static final String TAG_START = "\t<uses-permission android:name=\"";
    private static final String TAG_END = "\"/>";

    private final String value;

    private UsesPermission(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public String getXmlTag() {
        return TAG_START + value + TAG_END;
    }
}
