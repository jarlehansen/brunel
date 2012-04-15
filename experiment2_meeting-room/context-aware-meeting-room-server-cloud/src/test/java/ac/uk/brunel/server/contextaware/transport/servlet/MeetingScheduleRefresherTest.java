package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.server.contextaware.service.MeetingService;
import ac.uk.brunel.server.contextaware.transport.servlet.util.ServletMockUtil;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 31, 2010
 * Time: 11:00:39 PM
 */
public class MeetingScheduleRefresherTest {
    private HttpServletRequest mockedRequest;
    private HttpServletResponse mockedResponse;
    private MeetingService mockedMeetingService;

    private MeetingScheduleRefresher meetingScheduleRefresher;

    @Before
    public void setup() {
        mockedRequest = ServletMockUtil.createRequestMock();
        mockedResponse = ServletMockUtil.createResponseMock();
        mockedMeetingService = mock(MeetingService.class);

        meetingScheduleRefresher = new MeetingScheduleRefresher(mockedMeetingService);
    }

    @Test
    public void testRefreshMeetingSchedule() throws IOException {
        meetingScheduleRefresher.doGet(mockedRequest, mockedResponse);

        verify(mockedResponse).getWriter();
        verify(mockedMeetingService, times(2)).refreshMeetingList();
    }
}
