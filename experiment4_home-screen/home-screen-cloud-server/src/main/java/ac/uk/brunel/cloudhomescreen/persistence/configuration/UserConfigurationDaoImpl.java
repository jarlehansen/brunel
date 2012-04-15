package ac.uk.brunel.cloudhomescreen.persistence.configuration;

import ac.uk.brunel.cloudhomescreen.dto.UserConfiguration;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:29:23 PM - Nov 20, 2010
 */
public class UserConfigurationDaoImpl implements UserConfigurationDao, UserConfigurationRetrieverDao {
    private final Logger logger = LoggerFactory.getLogger(UserConfigurationDaoImpl.class);

    static {
        ObjectifyService.register(UserConfiguration.class);
    }

    public UserConfigurationDaoImpl() {
    }

    public void persistConfiguration(final UserConfiguration userConfiguration) {
        logger.info("Persisting:" + userConfiguration);

        final Objectify objectify = ObjectifyService.begin();
        objectify.put(userConfiguration);
    }

    public UserConfiguration getConfiguration(final String user) {
        final Objectify objectify = ObjectifyService.begin();
        UserConfiguration userConfiguration = null;

        try {
            userConfiguration = objectify.get(UserConfiguration.class, user);
        } catch (NotFoundException nfe) {
            logger.info(nfe.getMessage());
        }

        if (userConfiguration == null) {
            userConfiguration = initializeDefaultValues(user);
        }

        return userConfiguration;
    }

    private UserConfiguration initializeDefaultValues(final String user) {
        logger.info("Initializing user: " + user);

        final UserConfiguration defaultUserConfiguration = UserConfiguration.initializeDefaultUserConfiguration(user);

        ObjectifyService.begin().put(defaultUserConfiguration);

        return defaultUserConfiguration;
    }

    public void deleteAll() {
        logger.info("Deleting all persisted objects");
        
        final Objectify objectify = ObjectifyService.begin();
        Iterable<Key<UserConfiguration>> allKeys = objectify.query(UserConfiguration.class).fetchKeys();
        objectify.delete(allKeys);
    }
}
