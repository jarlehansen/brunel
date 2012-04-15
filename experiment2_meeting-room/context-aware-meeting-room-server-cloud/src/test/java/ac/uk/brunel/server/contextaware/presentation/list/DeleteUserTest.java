package ac.uk.brunel.server.contextaware.presentation.list;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 18, 2010
 * Time: 10:19:41 PM
 */
public class DeleteUserTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester();
        tester.startPage(DeleteUser.class);
    }

    @Test
    public void testRenderDeleteUser() {
        tester.assertRenderedPage(DeleteUser.class);
    }
}
