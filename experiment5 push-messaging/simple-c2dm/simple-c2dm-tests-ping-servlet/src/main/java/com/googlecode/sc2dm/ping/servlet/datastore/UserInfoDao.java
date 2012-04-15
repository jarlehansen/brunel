package com.googlecode.sc2dm.ping.servlet.datastore;

import com.googlecode.sc2dm.server.authentication.AuthenticationData;
import com.googlecode.sc2dm.server.sender.AuthTokenUpdater;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:05 PM - 10/6/11
 */
public interface UserInfoDao extends AuthTokenUpdater {
    public String getAuthToken();
}
