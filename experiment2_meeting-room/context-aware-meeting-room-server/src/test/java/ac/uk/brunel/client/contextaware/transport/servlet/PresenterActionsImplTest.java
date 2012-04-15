package ac.uk.brunel.client.contextaware.transport.servlet;

import ac.uk.brunel.client.contextaware.service.PresenterService;
import ac.uk.brunel.client.contextaware.transport.servlet.util.ServletInputStreamTester;
import ac.uk.brunel.client.contextaware.util.HttpTestFileInputUtil;
import ac.uk.brunel.contextaware.network.generated.PresenterActionProtobuf;
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
 * Date: Feb 18, 2010
 * Time: 8:34:59 PM
 */
public class PresenterActionsImplTest {
    private final PresenterActionProtobuf.PresenterAction presenterAction = PresenterActionProtobuf.PresenterAction.newBuilder().setAction("next").build();
    private PresenterActionsImpl presenterActions;

    private HttpServletRequest mockedRequest;
    private HttpServletResponse mockedResponse;
    private ServletInputStreamTester servletInputStreamTester;

    private PresenterService mockedPresenterService;

    @Before
    public void setup() throws IOException {
        mockedPresenterService = mock(PresenterService.class);
        
        mockedRequest = mock(HttpServletRequest.class);
        mockedResponse = mock(HttpServletResponse.class);
        servletInputStreamTester = new ServletInputStreamTester(HttpTestFileInputUtil.createTestInputStream(presenterAction));
        when(mockedRequest.getInputStream()).thenReturn(servletInputStreamTester);

        @SuppressWarnings("unchecked")
        Provider<PresenterService> mockedPresenterServiceProvider = mock(Provider.class);
        when(mockedPresenterServiceProvider.get()).thenReturn(mockedPresenterService);

        presenterActions = new PresenterActionsImpl(mockedPresenterServiceProvider);
    }

    @Test
    public void testReceivePresenterAction() {
        presenterActions.doGet(mockedRequest, mockedResponse);

        verify(mockedPresenterService).performPresenterAction(any(PresenterActionProtobuf.PresenterAction.class));

        assertTrue(servletInputStreamTester.closed());
    }

    @Test
    public void testReceivePresenterActionInputException() throws IOException {
        when(mockedRequest.getInputStream()).thenThrow(new IOException("thrown from JUnit test"));

        presenterActions.doGet(mockedRequest, mockedResponse);

        verifyZeroInteractions(mockedPresenterService);
    }
}
