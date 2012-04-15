package ac.uk.brunel.server.contextaware.presentation.register;

import ac.uk.brunel.server.contextaware.dto.User;
import ac.uk.brunel.server.contextaware.persistence.user.UserDao;
import ac.uk.brunel.server.contextaware.presentation.propertymodel.UserWicketPropertyModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 18, 2010
 * Time: 11:15:43 PM
 */
public class ConfirmRegistrationTest {
    private UserDao mockedUserDao;
    private WicketTester wicketTester;

    @Before
    public void setup() {
        mockedUserDao = mock(UserDao.class);
        wicketTester = new WicketTester();
    }

    @Test
    public void testRenderConfirmRegistration() {
        wicketTester.startPage(new ConfirmRegistration(mockedUserDao, new UserWicketPropertyModel()));

        wicketTester.assertRenderedPage(ConfirmRegistration.class);
        verify(mockedUserDao).registerUser(any(User.class));
    }
}
