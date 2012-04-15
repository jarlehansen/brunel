package ac.uk.brunel.benchmark.server.persistence;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:05 PM - 10/6/11
 */
public class GoogleAuthDaoImpl implements GoogleAuthDao {
    private static final Logger logger = LoggerFactory.getLogger(GoogleAuthDaoImpl.class);
    private static final String EMPTY_TOKEN = "REPLACE_WITH_AUTH_TOKEN";

    static {
        ObjectifyService.register(GoogleAuth.class);

        // initialize datastore
//        Objectify objectify = ObjectifyService.begin();
//        try {
//            GoogleAuth googleAuth = objectify.get(GoogleAuth.class, "AUTH_TOKEN");
//            logger.info("Using token: {}", googleAuth.getToken());
//        } catch (NotFoundException nfe) {
//            logger.info("No token found, inserting empty placeholder");
//            objectify.put(new GoogleAuth(EMPTY_TOKEN));
//        }
    }

    public GoogleAuthDaoImpl() {
    }

    public String getAuthToken() {
        String token = "";
        Objectify objectify = ObjectifyService.begin();

        try {
            GoogleAuth auth = objectify.get(GoogleAuth.class, new GoogleAuth().getId());
            token = auth.getToken();
        } catch (NotFoundException nfe) {
            logger.error("No token found, inserting empty placeholder", nfe);
            objectify.put(new GoogleAuth(EMPTY_TOKEN));
        }

        return token;
    }
}
