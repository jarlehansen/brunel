package ac.uk.brunel.server.contextaware.integration.calendar;

import com.google.gdata.client.calendar.CalendarService;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 24, 2010
 * Time: 5:03:07 PM
 */
public interface CalendarLogon {
    void accountLogon(final CalendarService calendarService);
}
