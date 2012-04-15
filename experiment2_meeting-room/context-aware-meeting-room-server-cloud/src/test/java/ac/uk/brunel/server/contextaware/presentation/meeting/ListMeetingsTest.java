package ac.uk.brunel.server.contextaware.presentation.meeting;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.persistence.meeting.MeetingDaoWicketFacade;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 25, 2010
 * Time: 9:53:20 PM
 */
public class ListMeetingsTest {
    private MeetingDaoWicketFacade mockedMeetingDaoWicketFacade;
    private WicketTester wicketTester;

    @Before
    public void setup() {
        mockedMeetingDaoWicketFacade = mock(MeetingDaoWicketFacade.class);
        wicketTester = new WicketTester();
    }

    @Test
    public void testRenderListMeetings() {
        wicketTester.startPage(new ListMeetings(mockedMeetingDaoWicketFacade));

        wicketTester.assertRenderedPage(ListMeetings.class);
        verify(mockedMeetingDaoWicketFacade).getTodaysMeetings(anyString());
    }

    @Test
    public void testRenderListMeetingsEmptyPresentationLink() {
        final List<Meeting> meetingList = new ArrayList<Meeting>();
        meetingList.add(new Meeting("meetingId", "", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime"));
        
        when(mockedMeetingDaoWicketFacade.getTodaysMeetings(anyString())).thenReturn(meetingList);
        wicketTester.startPage(new ListMeetings(mockedMeetingDaoWicketFacade));

        wicketTester.assertRenderedPage(ListMeetings.class);
        verify(mockedMeetingDaoWicketFacade).getTodaysMeetings(anyString());
    }
}
