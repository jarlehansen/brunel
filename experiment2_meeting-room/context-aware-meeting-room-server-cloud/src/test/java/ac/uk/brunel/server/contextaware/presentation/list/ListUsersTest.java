package ac.uk.brunel.server.contextaware.presentation.list;

import ac.uk.brunel.server.contextaware.persistence.user.UserDao;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 18, 2010
 * Time: 10:45:35 PM
 */
public class ListUsersTest {
    private UserDao mockedUserDao;
    private WicketTester wicketTester;

    @Before
    public void setup() {
        mockedUserDao = mock(UserDao.class);
        wicketTester = new WicketTester();
    }

    @Test
    public void testRenderListUsers() {
        wicketTester.startPage(new ListUsers(mockedUserDao));

        wicketTester.assertRenderedPage(ListUsers.class);
        verify(mockedUserDao).getAllUsers();
    }
}
