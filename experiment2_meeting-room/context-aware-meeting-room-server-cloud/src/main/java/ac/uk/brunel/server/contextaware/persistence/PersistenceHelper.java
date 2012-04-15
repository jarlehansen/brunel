package ac.uk.brunel.server.contextaware.persistence;

import com.google.inject.ImplementedBy;

import javax.persistence.EntityManager;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 14, 2010
 * Time: 4:30:11 PM
 */
@ImplementedBy(PersistenceHelperImpl.class)
// Wicket needs this
public interface PersistenceHelper {
    public EntityManager get();
}
