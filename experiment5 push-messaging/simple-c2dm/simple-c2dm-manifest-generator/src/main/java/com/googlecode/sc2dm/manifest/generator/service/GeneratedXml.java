package com.googlecode.sc2dm.manifest.generator.service;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:11 PM - 9/27/11
 */
public class GeneratedXml {
    private final String xmlPermissions;
    private final String xmlReceiver;
    private final String classReceiver;

    public GeneratedXml(String xmlPermissions, String xmlReceiver, String classReceiver) {
        this.xmlPermissions = xmlPermissions;
        this.xmlReceiver = xmlReceiver;
        this.classReceiver = classReceiver;
    }

    public String getXmlPermissions() {
        return xmlPermissions;
    }

    public String getXmlReceiver() {
        return xmlReceiver;
    }

    public String getClassReceiver() {
        return classReceiver;
    }
}
