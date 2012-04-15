package ac.uk.brunel.cloudhomescreen.config;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:12 PM - 1/10/11
 */
public class PropertiesLoaderTest {

    @Test
    public void testGetProperty() {
        String value = PropertiesLoader.APP.getProperty("c2dm.accounttype");

        assertEquals("GOOGLE", value);
    }

    @Test
    public void testGetPropertyInvalidKey() {
        String value = PropertiesLoader.APP.getProperty("invalid.key");

        assertEquals("", value);
    }
}
