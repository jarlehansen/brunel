package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.contextaware.network.generated.PresenterDetailsProtobuf;
import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.service.MeetingService;
import ac.uk.brunel.server.contextaware.transport.servlet.util.ServletInputStreamTester;
import ac.uk.brunel.server.contextaware.transport.servlet.util.ServletMockUtil;
import ac.uk.brunel.server.contextaware.transport.servlet.util.ServletOutputStreamTester;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 9:10:15 PM
 */
public class PresenterMeetingListSenderTest {
    private HttpServletRequest mockedRequest;
    private HttpServletResponse mockedResponse;
    private MeetingService mockedMeetingService;

    private Meeting meeting;

    private ServletInputStreamTester servletInputStream;
    private ServletOutputStreamTester servletOutputStream;

    private PresenterMeetingListSender presenterMeetingListSender;

    @Before
    public void setup() throws IOException {
        meeting = new Meeting("meetingId", "presentationLink", "presenter", new ArrayList<String>(), "title", "description", "location", "startTime", "endTime");
        final List<Meeting> meetingList = new ArrayList<Meeting>();
        meetingList.add(meeting);

        mockedRequest = ServletMockUtil.createRequestMock();
        mockedResponse = ServletMockUtil.createResponseMock();
        mockedMeetingService = mock(MeetingService.class);

        servletInputStream = ServletMockUtil.createServletInput(mockedRequest);
        servletOutputStream = ServletMockUtil.createServletOutput(mockedResponse);

        when(mockedMeetingService.getTodaysPresenterMeetings(anyString())).thenReturn(meetingList);

        presenterMeetingListSender = new PresenterMeetingListSender(mockedMeetingService);
    }

    @Test
    public void testGetUserMeetingsValidEmail() throws IOException {
        PresenterDetailsProtobuf.PresenterDetails validPresenterDetails = PresenterDetailsProtobuf.PresenterDetails
                .newBuilder().setEmail("hansjar@gmail.com").build();
        validPresenterDetails.writeTo(ServletMockUtil.createTestOutputFile());

        presenterMeetingListSender.doGet(mockedRequest, mockedResponse);

        verify(mockedMeetingService).getTodaysPresenterMeetings(anyString());
        assertTrue(servletOutputStream.getCounter() > 0);
        assertTrue(servletInputStream.closed());
        assertTrue(servletOutputStream.closed());
    }

    @Test
    public void testGetUserMeetingsNullEmail() throws IOException {
        PresenterDetailsProtobuf.PresenterDetails invalidPresenterDetails = PresenterDetailsProtobuf.PresenterDetails
                .newBuilder().setEmail("").build();
        invalidPresenterDetails.writeDelimitedTo(ServletMockUtil.createTestOutputFile());

        presenterMeetingListSender.doGet(mockedRequest, mockedResponse);

        verifyZeroInteractions(mockedMeetingService);
        assertEquals(0, servletOutputStream.getCounter());
        assertTrue(servletInputStream.closed());
        assertFalse(servletOutputStream.closed());
    }

    @Test
    public void testGetUserMeetingsThrowsException() throws IOException {
        HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
        when(mockedRequest.getInputStream()).thenThrow(new IOException("This is from testGetUserMeetingsThrowsException"));

        presenterMeetingListSender.doGet(mockedRequest, mockedResponse);

        verifyZeroInteractions(mockedMeetingService);
        assertEquals(0, servletOutputStream.getCounter());
        assertFalse(servletInputStream.closed());
        assertFalse(servletOutputStream.closed());
    }
}
