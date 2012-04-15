package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.contextaware.network.generated.NoteProtobuf;
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
import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 31, 2010
 * Time: 10:35:40 PM
 */
public class PresentationNotesSenderTest {
    private HttpServletRequest mockedRequest;
    private HttpServletResponse mockedResponse;
    private PresentationService mockedPresentationService;

    private ServletInputStreamTester servletInputStream;
    private ServletOutputStreamTester servletOutputStream;

    private PresentationNotesSender presentationNotesSender;

    @Before
    public void setup() throws IOException {
        mockedRequest = ServletMockUtil.createRequestMock();
        mockedResponse = ServletMockUtil.createResponseMock();
        mockedPresentationService = mock(PresentationService.class);

        @SuppressWarnings("unchecked")
        final Provider<PresentationService> mockedPresentationServiceProvider = mock(Provider.class);
        when(mockedPresentationServiceProvider.get()).thenReturn(mockedPresentationService);

        servletInputStream = ServletMockUtil.createServletInput(mockedRequest);
        servletOutputStream = ServletMockUtil.createServletOutput(mockedResponse);

        presentationNotesSender = new PresentationNotesSender(mockedPresentationServiceProvider);
    }

    @Test
    public void testSendCurrentPresentationNoteWithoutMeetingId() throws IOException {
        when(mockedPresentationService.getCurrentSlideNote("123456789012")).thenReturn(new String[]{"meetingId", "presentationNote1"});

        NoteProtobuf.Note inputNote = NoteProtobuf.Note.newBuilder().setBtAddress("123456789012").build();
        inputNote.writeTo(ServletMockUtil.createTestOutputFile());

        presentationNotesSender.doGet(mockedRequest, mockedResponse);

        verify(mockedPresentationService).getCurrentSlideNote("123456789012");
        assertTrue(servletOutputStream.getCounter() > 0);
        assertTrue(servletInputStream.closed());
        assertTrue(servletOutputStream.closed());
    }

    @Test
    public void testSendCurrentPresentationNoteWithMeetingId() throws IOException {
        when(mockedPresentationService.getCurrentSlideNote("meetingId", "123456789012")).thenReturn("presentationNote1");

        NoteProtobuf.Note inputNote = NoteProtobuf.Note.newBuilder().setMeetingId("meetingId").setBtAddress("123456789012").build();
        inputNote.writeTo(ServletMockUtil.createTestOutputFile());

        presentationNotesSender.doGet(mockedRequest, mockedResponse);

        verify(mockedPresentationService).getCurrentSlideNote("meetingId", "123456789012");
        assertTrue(servletOutputStream.getCounter() > 0);
        assertTrue(servletInputStream.closed());
        assertTrue(servletOutputStream.closed());
    }

    @Test
    public void testSendCurrentPresentationNoteInputExceptionThrown() throws IOException {
        when(mockedRequest.getInputStream()).thenThrow(new IOException("this is a test"));

        NoteProtobuf.Note inputNote = NoteProtobuf.Note.newBuilder().setMeetingId("meetingId").setBtAddress("123456789012").build();
        inputNote.writeTo(ServletMockUtil.createTestOutputFile());

        presentationNotesSender.doGet(mockedRequest, mockedResponse);
        verifyZeroInteractions(mockedResponse, mockedPresentationService);
    }

    @Test
    public void testSendCurrentPresentationNoteOutputExceptionThrown() throws IOException {
        when(mockedPresentationService.getCurrentSlideNote("meetingId", "123456789012")).thenReturn("presentationNote1");
        when(mockedResponse.getOutputStream()).thenThrow(new IOException("this is a test"));

        NoteProtobuf.Note inputNote = NoteProtobuf.Note.newBuilder().setMeetingId("meetingId").setBtAddress("123456789012").build();
        inputNote.writeTo(ServletMockUtil.createTestOutputFile());

        presentationNotesSender.doGet(mockedRequest, mockedResponse);
        verify(mockedPresentationService).getCurrentSlideNote("meetingId", "123456789012");
    }
}
