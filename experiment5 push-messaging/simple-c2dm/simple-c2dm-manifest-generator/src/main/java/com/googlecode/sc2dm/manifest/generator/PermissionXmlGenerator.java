package com.googlecode.sc2dm.manifest.generator;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:16 PM - 9/24/11
 */
public class PermissionXmlGenerator implements Generator {

    public PermissionXmlGenerator() {
    }

    public String generate(String packageName) {
        StringBuilder sb = new StringBuilder();
        sb.append("<uses-sdk android:minSdkVersion=\"8\" />\n\n");
        sb.append("<permission android:name=\"").append(packageName).append(".permission.C2D_MESSAGE\" android:protectionLevel=\"signature\"/>\n\n");
        sb.append("<uses-permission android:name=\"").append(packageName).append(".permission.C2D_MESSAGE\"/>\n");
        sb.append("<uses-permission android:name=\"com.google.android.c2dm.permission.RECEIVE\" />\n");
        sb.append("<uses-permission android:name=\"android.permission.INTERNET\" />\n");
        sb.append("<uses-permission android:name=\"android.permission.WAKE_LOCK\" />\n");

        return sb.toString();
    }
}
