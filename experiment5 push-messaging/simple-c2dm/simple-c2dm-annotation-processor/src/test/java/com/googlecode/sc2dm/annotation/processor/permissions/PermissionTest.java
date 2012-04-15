package com.googlecode.sc2dm.annotation.processor.permissions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:08 PM - 11/20/11
 */
public class PermissionTest {
    @Test
    public void value() {
        String value = "com.googlecode.sc2dm.activity.permission.C2D_MESSAGE";
        assertEquals(value, Permission.C2D_MESSAGE.value());
    }

    @Test
    public void getXmlTag() {
        String xmlTag = "<permission android:name=\"com.googlecode.sc2dm.activity.permission.C2D_MESSAGE\" android:protectionLevel=\"signature\"/>";
        assertEquals(xmlTag, Permission.C2D_MESSAGE.getXmlTag());
    }
}
