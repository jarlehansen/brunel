package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.contextaware.network.generated.UserListProtobuf;
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
 * Date: Jan 31, 2010
 * Time: 6:10:21 PM
 */
public class MeetingUsersRegistrationTest {
    private HttpServletRequest mockedRequest;
    private HttpServletResponse mockedResponse;
    private PresentationService mockedPresentationService;

    private ServletInputStreamTester servletInputStream;

    private MeetingUsersRegistration meetingUsersRegistration;

    @Before
    public void setup() throws IOException {
        mockedRequest = ServletMockUtil.createRequestMock();
        mockedResponse = ServletMockUtil.createResponseMock();
        mockedPresentationService = mock(PresentationService.class);

        @SuppressWarnings("unchecked")
        final Provider<PresentationService> mockedPresentationServiceProvider = mock(Provider.class);
        when(mockedPresentationServiceProvider.get()).thenReturn(mockedPresentationService);

        servletInputStream = ServletMockUtil.createServletInput(mockedRequest);

        meetingUsersRegistration = new MeetingUsersRegistration(mockedPresentationServiceProvider);
    }

    @Test
    public void testRegisterUser() throws IOException {
        UserListProtobuf.UserList userList = UserListProtobuf.UserList.newBuilder().setMeetingId("meetingId")
                .addBtaddress("123456789012").addBtaddress("000000000000").build();
        userList.writeTo(ServletMockUtil.createTestOutputFile());

        meetingUsersRegistration.doGet(mockedRequest, mockedResponse);

        verify(mockedPresentationService).registerConnectedUser("meetingId", "123456789012");
        verify(mockedPresentationService).registerConnectedUser("meetingId", "000000000000");
        assertTrue(servletInputStream.closed());
    }

    @Test
    public void testRegisterUserNoBtAddresses() throws IOException {
        UserListProtobuf.UserList userList = UserListProtobuf.UserList.newBuilder().setMeetingId("meetingId").build();
        userList.writeDelimitedTo(ServletMockUtil.createTestOutputFile());

        meetingUsersRegistration.doGet(mockedRequest, mockedResponse);

        verifyZeroInteractions(mockedPresentationService);
        assertTrue(servletInputStream.closed());
    }
}
