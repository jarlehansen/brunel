package ac.uk.brunel.server.contextaware.persistence.user;

import ac.uk.brunel.server.contextaware.dto.User;
import ac.uk.brunel.server.contextaware.persistence.PersistenceHelper;
import ac.uk.brunel.server.contextaware.persistence.wrapper.UserJpaWrapper;
import ac.uk.brunel.server.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import ac.uk.brunel.server.contextaware.properties.QueriesConstants;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 14, 2010
 * Time: 7:21:05 PM
 */
public class UserDaoImpl implements UserDao {
    @Inject
    private PersistenceHelper persistenceHelper; // Wicket needs it to be field injection

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl() {
    }

    /**
     * Used for tests only!
     */
    UserDaoImpl(final PersistenceHelper persistenceHelper) {
        this.persistenceHelper = persistenceHelper;
    }

    public void registerUser(final User user) {
        final EntityManager em = persistenceHelper.get();

        if (logger.isDebugEnabled()) {
            logger.debug("Registering user: " + user);
        }

        try {
            em.persist(new UserJpaWrapper(user));
        } catch (PersistenceException pe) {
            if (logger.isErrorEnabled()) {
                logger.error("Unable to persist user: " + user, pe);
            }
        } finally {
            em.close();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();

        final EntityManager em = persistenceHelper.get();

        try {
            Query query = em.createQuery(PropertiesReader.JPA_QUERIES.get(QueriesConstants.USERDAO_SELECTALL));

            // Convert from UserJpaWrapper to User objects
            @SuppressWarnings("unchecked")
            final List<UserJpaWrapper> tempList = query.getResultList();

            if (tempList != null) {
                for (UserJpaWrapper tempUser : tempList) {
                    userList.add(tempUser.getUser());
                }
            }
        } finally {
            em.close();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Returning user list: " + userList);
        }

        return userList;
    }

    public void deleteUser(final String secret, final String email) {
        if (PropertiesReader.SERVER_APP.get(PropertiesConstants.ADMIN_SECRET).equals(secret)) {

            final EntityManager em = persistenceHelper.get();

            try {
                Query query = em.createQuery(PropertiesReader.JPA_QUERIES.get(QueriesConstants.USERDAO_DELETEUSER));
                query.setParameter(1, email);
                final int numOfRows = query.executeUpdate();

                if (logger.isDebugEnabled()) {
                    logger.debug("Trying to delete " + email, " number of rows deleted: " + numOfRows);
                }
            } finally {
                em.close();
            }
        } else if (logger.isInfoEnabled()) {
            logger.info("When trying to delete user " + email + " wrong password was entered");
        }
    }

    public User findUser(final String email) {
        User user = User.EMPTY_USER;

        final EntityManager em = persistenceHelper.get();

        try {
            UserJpaWrapper tempUser = em.find(UserJpaWrapper.class, email);

            if (tempUser != null) {
                user = tempUser.getUser();
            } else {
                User aliasUser = findAliasUser(em, email);

                if (aliasUser != null) {
                    user = aliasUser;
                }
            }
        } finally {
            em.close();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Found user: " + user);
        }

        return user;
    }

    private User findAliasUser(final EntityManager em, final String email) {
        // Try querying with an alias (email accounts can be converted to @googlemail.com)
        String[] emailArgs = email.split("@");
        String emailName = emailArgs[0];

        if (logger.isInfoEnabled()) {
            logger.info("No user found with e-mail: " + email + ", trying with alias " + emailName);
        }

        Query query = em.createQuery(PropertiesReader.JPA_QUERIES.get(QueriesConstants.USERDAO_SELECTUSERALIAS));
        query.setParameter(1, emailName + "%");

        @SuppressWarnings("unchecked")
        List<UserJpaWrapper> aliasUsers = query.getResultList();

        User user = null;
        if (aliasUsers.size() > 0) {
            user = aliasUsers.get(0).getUser();
        }

        return user;
    }
}
