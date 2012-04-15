package ac.uk.brunel.server.contextaware.presentation.find;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 18, 2010
 * Time: 10:43:51 PM
 */
public class FindUserTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester();
        tester.startPage(FindUser.class);
    }

    @Test
    public void testRenderFindUser() {
        tester.assertRenderedPage(FindUser.class);
    }
}
