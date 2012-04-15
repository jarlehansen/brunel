package ac.uk.brunel.server.contextaware.presentation.register;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 18, 2010
 * Time: 11:13:08 PM
 */
public class RegisterUserTest {
    private WicketTester wicketTester;

    @Before
    public void setup() {
        wicketTester = new WicketTester();
        wicketTester.startPage(RegisterUser.class);
    }

    @Test
    public void testRenderRegisterUser() {
        wicketTester.assertRenderedPage(RegisterUser.class);
    }
}
