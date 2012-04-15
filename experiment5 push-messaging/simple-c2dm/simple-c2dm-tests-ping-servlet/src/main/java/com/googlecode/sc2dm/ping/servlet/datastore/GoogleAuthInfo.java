package com.googlecode.sc2dm.ping.servlet.datastore;

import javax.persistence.Id;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:08 PM - 10/6/11
 */
public class GoogleAuthInfo {
    @Id
    private String id = "AUTH_TOKEN";
    private String token;

    public GoogleAuthInfo() {
    }

    public GoogleAuthInfo(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
