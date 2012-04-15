package ac.uk.brunel.server.contextaware.persistence;

import com.google.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 14, 2010
 * Time: 4:00:05 PM
 */
@Singleton
public class PersistenceHelperImpl implements PersistenceHelper {
    private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("transactions-optional");

    public synchronized EntityManager get() {
        return emfInstance.createEntityManager();
    }
}
