package ac.uk.brunel.server.contextaware.integration.calendar;

import com.google.gdata.client.calendar.CalendarService;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 24, 2010
 * Time: 5:26:57 PM
 */
public class CalendarLogonImplIntegrationTest {
    private CalendarLogon calendarLogon;
    private CalendarService calendarService;

    @Before
    public void setup() {
        calendarLogon = new CalendarLogonImpl();
        calendarService = new CalendarService("Gmail calendar integration");
    }

    @Test
    public void testAccountLogon() {
        calendarLogon.accountLogon(calendarService);
    }
}
