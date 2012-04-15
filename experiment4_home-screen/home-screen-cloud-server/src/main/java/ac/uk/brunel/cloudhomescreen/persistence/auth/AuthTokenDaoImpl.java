package ac.uk.brunel.cloudhomescreen.persistence.auth;

import ac.uk.brunel.cloudhomescreen.constants.HomeScreenConstants;
import ac.uk.brunel.cloudhomescreen.dto.AuthToken;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 1:58 PM - 1/8/11
 */
public class AuthTokenDaoImpl implements AuthTokenDao {
    private final Logger logger = LoggerFactory.getLogger(AuthTokenDaoImpl.class);

    static {
        ObjectifyService.register(AuthToken.class);
    }

    public AuthTokenDaoImpl() {
    }

    public void persistToken(final AuthToken token) {
        logger.info("Persisting: " + token);

        final Objectify objectify = ObjectifyService.begin();
        objectify.put(token);
    }

    public AuthToken getToken() {
        final Objectify objectify = ObjectifyService.begin();
        AuthToken token = null;

        try {
            token = objectify.get(AuthToken.class, HomeScreenConstants.AUTH_TOKEN_KEY);
        } catch(NotFoundException nfe) {
            logger.info("Authentication token not found in datastore");
        }

        return token;
    }
}
