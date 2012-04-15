package com.googlecode.sc2dm.annotation.processor.permissions;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:04 PM - 11/20/11
 */
public enum Permission {
    C2D_MESSAGE("com.googlecode.sc2dm.activity.permission.C2D_MESSAGE");

    private static final String TAG_START = "\t<permission android:name=\"";
    private static final String TAG_END = "\" android:protectionLevel=\"signature\"/>";

    private final String value;

    private Permission(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public String getXmlTag() {
        return TAG_START + value + TAG_END;
    }
}
