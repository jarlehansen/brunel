package ac.uk.brunel.server.contextaware.service;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.integration.calendar.CalendarIntegration;
import ac.uk.brunel.server.contextaware.persistence.meeting.MeetingDao;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 22, 2010
 * Time: 6:23:24 PM
 */
public class MeetingServiceImplTest {
    private MeetingService meetingService;
    private CalendarIntegration mockedCalendarIntegration;
    private MeetingDao mockedMeetingDao;

    private List<Meeting> meetingList;

    @Before
    public void setup() {
        final Meeting meeting = new Meeting("meetingId", "presentationLink", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");
        meetingList = new ArrayList<Meeting>();
        meetingList.add(meeting);
        meetingList.add(meeting);

        mockedCalendarIntegration = mock(CalendarIntegration.class);
        when(mockedCalendarIntegration.getMeetingList(any(Date.class), any(Date.class))).thenReturn(meetingList);

        mockedMeetingDao = mock(MeetingDao.class);
        meetingService = new MeetingServiceImpl(mockedCalendarIntegration, mockedMeetingDao);
    }

    @Test
    public void testUpdateSchedule() {
        meetingService.refreshMeetingList();

        verify(mockedCalendarIntegration, times(1)).getMeetingList(any(Date.class), any(Date.class));
        verify(mockedMeetingDao, times(2)).registerMeeting(any(Meeting.class));
        verifyNoMoreInteractions(mockedCalendarIntegration, mockedMeetingDao);
    }
}
