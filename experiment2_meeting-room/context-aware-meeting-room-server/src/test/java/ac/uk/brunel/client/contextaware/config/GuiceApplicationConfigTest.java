package ac.uk.brunel.client.contextaware.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 16, 2010
 * Time: 8:00:46 PM
 */
public class GuiceApplicationConfigTest {
    private Injector injector;

    @Before
    public void setup() {
        injector = Guice.createInjector(new ServerAppModule());
    }

    @Test
    public void testGuiceConfig() {
        assertFalse(injector == null);
    }
}
