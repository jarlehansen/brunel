package ac.uk.brunel.server.contextaware.config;

import ac.uk.brunel.server.contextaware.presentation.HomePage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 19, 2010
 * Time: 11:02:40 PM
 */
public class WicketWebApplicationSettingsTest {

    @Test
    public void testHomePage() {
        final WicketWebApplicationSettings wepAppSettings = new WicketWebApplicationSettings();
        assertEquals(wepAppSettings.getHomePage(), HomePage.class);
    }
}
