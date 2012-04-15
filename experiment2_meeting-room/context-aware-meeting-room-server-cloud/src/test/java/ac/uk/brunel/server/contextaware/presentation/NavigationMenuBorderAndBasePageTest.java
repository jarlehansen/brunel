package ac.uk.brunel.server.contextaware.presentation;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 18, 2010
 * Time: 10:11:20 PM
 */
public class NavigationMenuBorderAndBasePageTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester();
        tester.startPage(DummyWebPage.class);
    }

    @Test
    public void testRenderNavigationMenuBorderAndBasePage() {
        tester.assertRenderedPage(DummyWebPage.class);
    }

    @Test
    public void testTitleTag() {
        assertTrue(tester.getTagByWicketId("title").getValue().length() > 0);
    }
}
