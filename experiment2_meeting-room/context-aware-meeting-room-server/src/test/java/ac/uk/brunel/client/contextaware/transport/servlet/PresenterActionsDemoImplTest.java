package ac.uk.brunel.client.contextaware.transport.servlet;

import ac.uk.brunel.client.contextaware.service.PresenterService;
import ac.uk.brunel.contextaware.network.generated.PresenterActionProtobuf;
import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 20, 2010
 * Time: 5:43:07 PM
 */
public class PresenterActionsDemoImplTest {
    private HttpServletRequest mockedRequest;
    private PresenterService mockedPresenterService;

    private PresenterActionsDemoImpl presenterActionsDemo;

    @Before
    public void setup() {
        mockedRequest = mock(HttpServletRequest.class);
        mockedPresenterService = mock(PresenterService.class);

        @SuppressWarnings("unchecked")
        final Provider<PresenterService> mockedPresenterServiceProvider = mock(Provider.class);
        when(mockedPresenterServiceProvider.get()).thenReturn(mockedPresenterService);

        presenterActionsDemo = new PresenterActionsDemoImpl(mockedPresenterServiceProvider);
    }

    @Test
    public void testCreatePresenterActionNextSlide() {
        when(mockedRequest.getParameter("action")).thenReturn("next");
        
        presenterActionsDemo.createPresenterActions(mockedRequest);

        verify(mockedPresenterService).performPresenterAction(any(PresenterActionProtobuf.PresenterAction.class));
    }

    @Test
    public void testCreatePresenterActionPreviousSlide() {
        when(mockedRequest.getParameter("action")).thenReturn("previous");

        presenterActionsDemo.createPresenterActions(mockedRequest);

        verify(mockedPresenterService).performPresenterAction(any(PresenterActionProtobuf.PresenterAction.class));
    }
}
