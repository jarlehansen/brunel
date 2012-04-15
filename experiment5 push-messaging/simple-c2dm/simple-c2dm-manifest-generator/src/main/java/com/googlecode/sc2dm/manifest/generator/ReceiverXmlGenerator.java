package com.googlecode.sc2dm.manifest.generator;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:32 AM - 9/25/11
 */
public class ReceiverXmlGenerator implements Generator {

    public ReceiverXmlGenerator() {
    }

    public String generate(String packageName) {
        StringBuilder sb = new StringBuilder();
        sb.append("<service android:name=\".C2DMReceiver\" />\n\n");
        sb.append("<receiver android:name=\"com.google.android.c2dm.C2DMBroadcastReceiver\"\n");
        sb.append("\t\tandroid:permission=\"com.google.android.c2dm.permission.SEND\">\n");
        sb.append("\t<intent-filter>\n");
        sb.append("\t\t<action android:name=\"com.google.android.c2dm.intent.RECEIVE\"/>\n");
        sb.append("\t\t<category android:name=\"").append(packageName).append("\"/>\n");
        sb.append("\t</intent-filter>\n");
        sb.append("\t<intent-filter>\n");
        sb.append("\t\t<action android:name=\"com.google.android.c2dm.intent.REGISTRATION\"/>\n");
        sb.append("\t\t<category android:name=\"").append(packageName).append("\"/>\n");
        sb.append("\t</intent-filter>\n");
        sb.append("</receiver>");

        return sb.toString();
    }
}
