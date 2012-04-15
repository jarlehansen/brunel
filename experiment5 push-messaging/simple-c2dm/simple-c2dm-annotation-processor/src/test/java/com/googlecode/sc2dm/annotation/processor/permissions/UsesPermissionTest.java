package com.googlecode.sc2dm.annotation.processor.permissions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:13 PM - 11/20/11
 */
public class UsesPermissionTest {
    @Test
    public void value() {
        String value = "com.googlecode.sc2dm.activity.permission.C2D_MESSAGE";
        assertEquals(value, UsesPermission.C2D_MESSAGE.value());
    }

    @Test
    public void getXmlTag() {
        String xmlTag = "<uses-permission android:name=\"com.googlecode.sc2dm.activity.permission.C2D_MESSAGE\"/>";
        assertEquals(xmlTag, UsesPermission.C2D_MESSAGE.getXmlTag());
    }
}
