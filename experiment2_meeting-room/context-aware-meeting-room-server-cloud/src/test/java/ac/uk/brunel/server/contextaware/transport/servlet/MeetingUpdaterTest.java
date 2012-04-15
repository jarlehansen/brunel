package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.contextaware.network.generated.PresenterEventProtobuf;
import ac.uk.brunel.server.contextaware.service.PresentationService;
import ac.uk.brunel.server.contextaware.transport.servlet.util.ServletInputStreamTester;
import ac.uk.brunel.server.contextaware.transport.servlet.util.ServletMockUtil;
import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 30, 2010
 * Time: 10:50:43 PM
 */
public class MeetingUpdaterTest {
    private HttpServletRequest mockedRequest;
    private HttpServletResponse mockedResponse;
    private PresentationService mockedPresentationService;

    private ServletInputStreamTester servletInputStream;

    private MeetingUpdater meetingUpdater;

    @Before
    public void setup() throws IOException {
        mockedRequest = ServletMockUtil.createRequestMock();
        mockedResponse = ServletMockUtil.createResponseMock();
        mockedPresentationService = mock(PresentationService.class);

        @SuppressWarnings("unchecked")
        final Provider<PresentationService> mockedPresentationServiceProvider = mock(Provider.class);
        when(mockedPresentationServiceProvider.get()).thenReturn(mockedPresentationService);

        servletInputStream = ServletMockUtil.createServletInput(mockedRequest);

        meetingUpdater = new MeetingUpdater(mockedPresentationServiceProvider);
    }

    @Test
    public void testUpdateMeeting() throws IOException {
        PresenterEventProtobuf.PresenterEvent presenterEvent = PresenterEventProtobuf.PresenterEvent.newBuilder().setMeetingId("meetingId")
                .setSlideNumber(5).build();
        presenterEvent.writeTo(ServletMockUtil.createTestOutputFile());

        meetingUpdater.doGet(mockedRequest, mockedResponse);

        verify(mockedPresentationService).updateCurrentSlideNumber("meetingId", 5);
        assertTrue(servletInputStream.closed());
    }
}
