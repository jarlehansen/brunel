package ac.uk.brunel.server.contextaware.persistence.user;

import ac.uk.brunel.server.contextaware.dto.User;
import ac.uk.brunel.server.contextaware.persistence.PersistenceHelper;
import ac.uk.brunel.server.contextaware.persistence.wrapper.UserJpaWrapper;
import ac.uk.brunel.server.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 16, 2010
 * Time: 10:33:58 PM
 */
public class UserDaoImplTest {
    private final User user = new User("hansjar@gmail.com", "Jarle", "Hansen", "123456789012");

    private EntityManager mockedEntityManager;
    private Query mockedQuery;

    private UserDaoImpl userDao;

    private InOrder inOrder;

    @Before
    public void setUp() {
        final PersistenceHelper mockedPersistenceHelper = mock(PersistenceHelper.class);
        mockedEntityManager = mock(EntityManager.class);
        mockedQuery = mock(Query.class);

        when(mockedPersistenceHelper.get()).thenReturn(mockedEntityManager);
        when(mockedEntityManager.createQuery(anyString())).thenReturn(mockedQuery);

        inOrder = inOrder(mockedEntityManager, mockedQuery);

        userDao = new UserDaoImpl(mockedPersistenceHelper);
    }

    @Test
    public void testRegisterUser() {
        userDao.registerUser(user);

        inOrder.verify(mockedEntityManager).persist(any(UserJpaWrapper.class));
        inOrder.verify(mockedEntityManager).close();
    }

    @Test
    public void testGetAllUsersWithListContent() {
        final List<UserJpaWrapper> userJpaList = new ArrayList<UserJpaWrapper>();
        userJpaList.add(new UserJpaWrapper(user));

        when(mockedQuery.getResultList()).thenReturn(new ArrayList<UserJpaWrapper>(userJpaList));

        final List<User> userList = userDao.getAllUsers();

        inOrder.verify(mockedEntityManager).createQuery(anyString());
        inOrder.verify(mockedQuery).getResultList();
        inOrder.verify(mockedEntityManager).close();

        assertReflectionEquals(user, userList.get(0));
    }

    @Test
    public void testGetAllUsersEmptyList() {
        final List<UserJpaWrapper> userJpaList = new ArrayList<UserJpaWrapper>();
        userJpaList.add(new UserJpaWrapper(user));

        when(mockedQuery.getResultList()).thenReturn(new ArrayList<UserJpaWrapper>());

        final List<User> userList = userDao.getAllUsers();

        inOrder.verify(mockedEntityManager).createQuery(anyString());
        inOrder.verify(mockedQuery).getResultList();
        inOrder.verify(mockedEntityManager).close();

        assertEquals(userList.size(), 0);
    }

    @Test
    public void testGetAllUsersNullList() {
        final List<UserJpaWrapper> userJpaList = new ArrayList<UserJpaWrapper>();
        userJpaList.add(new UserJpaWrapper(user));

        when(mockedEntityManager.createQuery(anyString())).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(null);

        final List<User> userList = userDao.getAllUsers();

        inOrder.verify(mockedEntityManager).createQuery(anyString());
        inOrder.verify(mockedQuery).getResultList();
        inOrder.verify(mockedEntityManager).close();

        assertEquals(userList.size(), 0);
    }

    @Test
    public void testDeleteUserValidSecret() {
        userDao.deleteUser(PropertiesReader.SERVER_APP.get(PropertiesConstants.ADMIN_SECRET), "hansjar@gmail.com");

        inOrder.verify(mockedEntityManager).createQuery(anyString());
        inOrder.verify(mockedQuery).executeUpdate();
        inOrder.verify(mockedEntityManager).close();
    }

    @Test
    public void testDeleteUserInvalidSecret() {
        userDao.deleteUser("not correct", "hansjar@gmail.com");

        verifyZeroInteractions(mockedEntityManager, mockedQuery);
    }

    @Test
    public void testFindUser() {
        final UserJpaWrapper userJpa = new UserJpaWrapper(user);
        when(mockedEntityManager.find(UserJpaWrapper.class, user.getEmail())).thenReturn(userJpa);

        final User tempUser = userDao.findUser(user.getEmail());

        verify(mockedEntityManager).find(UserJpaWrapper.class, user.getEmail());
        verify(mockedEntityManager).close();

        assertReflectionEquals(user, tempUser);
    }
}
