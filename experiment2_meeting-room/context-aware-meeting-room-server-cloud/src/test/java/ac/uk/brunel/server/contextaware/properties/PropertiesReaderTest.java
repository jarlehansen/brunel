package ac.uk.brunel.server.contextaware.properties;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 17, 2010
 * Time: 8:23:06 PM
 */
public class PropertiesReaderTest {
    private static final String TEST_KEY = "admin.secret";
    private static final String TEST_VALUE = "contextor";

    @Test
    public void testGetValidKey() {
        final String value = PropertiesReader.SERVER_APP.get(TEST_KEY);
        assertEquals(TEST_VALUE, value);
    }

    @Test
    public void testGetInvalidKey() {
        final String value = PropertiesReader.SERVER_APP.get("not a key");
        assertTrue(value.length() == 0);
    }

    @Test
    public void testGetNullKey() {
        final String value = PropertiesReader.SERVER_APP.get(null);
        assertTrue(value.length() == 0);
    }
}
