package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.contextaware.network.generated.MeetingProtobuf;
import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.dto.MeetingNote;
import ac.uk.brunel.server.contextaware.service.PresentationService;
import ac.uk.brunel.server.contextaware.transport.servlet.util.ServletInputStreamTester;
import ac.uk.brunel.server.contextaware.transport.servlet.util.ServletMockUtil;
import ac.uk.brunel.server.contextaware.transport.servlet.util.ServletOutputStreamTester;
import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 12, 2010
 * Time: 3:24:03 PM
 */
public class MeetingRegistrationTest {
    private HttpServletRequest mockedRequest;
    private HttpServletResponse mockedResponse;
    private PresentationService mockedPresentationService;
    private MeetingNote meetingNote;

    private ServletInputStreamTester servletInputStream;
    private ServletOutputStreamTester servletOutputStream;

    private MeetingRegistration meetingRegistration;

    @Before
    public void setup() throws IOException {
        mockedRequest = ServletMockUtil.createRequestMock();
        mockedResponse = ServletMockUtil.createResponseMock();
        mockedPresentationService = mock(PresentationService.class);

        meetingNote = new MeetingNote.Builder("meetingId").build();

        @SuppressWarnings("unchecked")
        final Provider<PresentationService> mockedPresentationServiceProvider = mock(Provider.class);
        when(mockedPresentationServiceProvider.get()).thenReturn(mockedPresentationService);
        when(mockedPresentationService.registerStartedMeeting(any(Meeting.class))).thenReturn(meetingNote);

        servletInputStream = ServletMockUtil.createServletInput(mockedRequest);
        servletOutputStream = ServletMockUtil.createServletOutput(mockedResponse);

        meetingRegistration = new MeetingRegistration(mockedPresentationServiceProvider);
    }

    @Test
    public void testRegisterMeeting() throws IOException {
        MeetingProtobuf.Meeting protoMeeting = MeetingProtobuf.Meeting.newBuilder().setMeetingId("meetingId")
                .setPresentationLink("presentationLink").setPresenter("presenter").addParticipants("participant")
                .setTitle("title").setDescription("description").setLocation("location").setStartTime("startTime")
                .setEndTime("endTime").build();
        protoMeeting.writeTo(ServletMockUtil.createTestOutputFile());

        meetingRegistration.doGet(mockedRequest, mockedResponse);

        verify(mockedPresentationService).registerStartedMeeting(any(Meeting.class));
        assertTrue(servletOutputStream.getCounter() > 0);
        assertTrue(servletOutputStream.closed());
        assertTrue(servletInputStream.closed());
    }
}
