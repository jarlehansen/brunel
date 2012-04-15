package ac.uk.brunel.server.contextaware.integration.calendar;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 22, 2010
 * Time: 12:00:28 AM
 */
public class CalendarIntegrationImplIntegrationTest {
    private final SimpleDateFormat scheduleDay = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat queryDate = new SimpleDateFormat("HH:mm:ss-dd/MM/yyyy");
    private final String fromTxt = "00:00:01";
    private final String toTxt = "23:59:59";
    
    private Date fromDate;
    private Date toDate;

    private CalendarIntegration calendarIntegration;

    @Before
    public void setup() throws ParseException {
        calendarIntegration = new CalendarIntegrationImpl(new CalendarLogonImpl());

         fromDate = queryDate.parse(fromTxt + "-" + scheduleDay.format(new Date()));
         toDate = queryDate.parse(toTxt + "-" + scheduleDay.format(new Date()));
    }

    @Test
    public void testCalendarAccountLogon() {
        List<Meeting> meetings = calendarIntegration.getMeetingList(fromDate, toDate);
        System.out.println("Number of meetings: " + meetings.size());

        for(Meeting meeting : meetings) {
            System.out.println(meeting.toString());
        }
    }
}
