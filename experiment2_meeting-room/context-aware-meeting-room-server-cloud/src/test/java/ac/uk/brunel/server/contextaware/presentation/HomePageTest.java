package ac.uk.brunel.server.contextaware.presentation;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 18, 2010
 * Time: 9:55:45 PM
 */
public class HomePageTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester();
        tester.startPage(HomePage.class);
    }


    @Test
    public void testRenderHomePage() {
        tester.assertRenderedPage(HomePage.class);
    }

    @Test
    public void testMessageTag() {
        assertTrue(tester.getTagByWicketId("message").getValue().length() > 0);
        assertTrue(tester.getTagByWicketId("timestamp").getValue().length() > 0);
    }
}
