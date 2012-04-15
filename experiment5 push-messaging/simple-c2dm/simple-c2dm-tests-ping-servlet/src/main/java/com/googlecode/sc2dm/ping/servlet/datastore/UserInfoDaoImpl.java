package com.googlecode.sc2dm.ping.servlet.datastore;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.sc2dm.server.sender.AuthTokenUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:05 PM - 10/6/11
 */
public class UserInfoDaoImpl implements UserInfoDao, AuthTokenUpdater {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoDaoImpl.class);
    private static final String EMPTY_TOKEN = "REPLACE_WITH_AUTH_TOKEN";

    static {
        ObjectifyService.register(GoogleAuthInfo.class);
    }

    public UserInfoDaoImpl() {
        getAuthToken(); // initialize datastore
    }

    public String getAuthToken() {
        String token = "";
        Objectify objectify = ObjectifyService.begin();

        try {
            GoogleAuthInfo authInfo = objectify.get(GoogleAuthInfo.class, new GoogleAuthInfo().getId());
            token = authInfo.getToken();
        } catch (NotFoundException nfe) {
            logger.info("No data found, inserting empty row", nfe);
            objectify.put(new GoogleAuthInfo(EMPTY_TOKEN));
        }

        return token;
    }
    
    @Override
    public void updateToken(String updatedToken) {
        logger.info("Persisting new token: {}", updatedToken);

        Objectify objectify = ObjectifyService.begin();
        objectify.put(new GoogleAuthInfo(updatedToken));
    }
}
