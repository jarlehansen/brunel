package com.googlecode.sc2dm.sender;

import junit.framework.TestCase;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 1:38 PM - 10/6/11
 */
public class RegIdSenderUtilTest extends TestCase {

    public void testIncompleteInput_missingServerUrl() {
        boolean wasSent = RegIdSenderUtil.sendRegistrationId("", "reg-id");

        assertFalse(wasSent);
    }

    public void testIncompleteInput_nullServerUrl() {
        boolean wasSent = RegIdSenderUtil.sendRegistrationId(null, "reg-id");

        assertFalse(wasSent);
    }

    public void testIncompleteInput_emptyRegId() {
        boolean wasSent = RegIdSenderUtil.sendRegistrationId("http://test", "");

        assertFalse(wasSent);
    }

    public void testIncompleteInput_nullRegId() {
        boolean wasSent = RegIdSenderUtil.sendRegistrationId("http://test", null);

        assertFalse(wasSent);
    }
}
