package ac.uk.brunel.server.contextaware.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 18, 2010
 * Time: 11:42:55 PM
 */
public class GuiceApplicationConfigTest {
    private Injector injector;

    @Before
    public void setup() {
        injector = Guice.createInjector(new MeetingUserModule());
    }

    @Test
    public void testGuiceConfig() {
        assertFalse(injector == null);
    }
}
