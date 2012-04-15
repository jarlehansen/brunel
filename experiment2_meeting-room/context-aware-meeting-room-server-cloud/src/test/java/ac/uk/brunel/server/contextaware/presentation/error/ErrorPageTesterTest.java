package ac.uk.brunel.server.contextaware.presentation.error;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 4:13:09 PM
 */
public class ErrorPageTesterTest {
    private WicketTester tester;

    @Before
    public void setup() {
        tester = new WicketTester();
    }

    @Test(expected = WicketRuntimeException.class)
    public void testRenderErrorPageTester() {
        tester.startPage(ErrorPageTester.class);
    }
}
