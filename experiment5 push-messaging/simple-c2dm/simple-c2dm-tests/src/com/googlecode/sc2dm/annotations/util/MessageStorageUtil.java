package com.googlecode.sc2dm.annotations.util;

import android.content.Intent;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:32 PM - 9/19/11
 */
public enum MessageStorageUtil {
    REG_ID,
    MSG;

    private String identifier;

    public void reset() {
        identifier = "";
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
