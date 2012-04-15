package ac.uk.brunel.server.contextaware.integration.calendar;

import ac.uk.brunel.server.contextaware.exception.calendar.CalendarCommunicationException;
import com.google.gdata.client.calendar.CalendarService;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 24, 2010
 * Time: 5:29:58 PM
 */
public class CalendarIntegrationImplTest {
    private CalendarIntegration calendarIntegration;

    @Before
    public void setup() {
        CalendarLogon mockedCalendarLogon = mock(CalendarLogon.class);
        doThrow(new CalendarCommunicationException("This is a JUnit test exception"))
                .when(mockedCalendarLogon).accountLogon(any(CalendarService.class));
        calendarIntegration = new CalendarIntegrationImpl(mockedCalendarLogon);
    }

    @Test(expected = CalendarCommunicationException.class)
    public void testGetMeetingListAuthenticationException() {
        calendarIntegration.getMeetingList(new Date(), new Date());
    }
}
