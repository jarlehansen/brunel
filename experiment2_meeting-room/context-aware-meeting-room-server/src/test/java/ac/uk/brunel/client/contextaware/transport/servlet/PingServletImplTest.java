package ac.uk.brunel.client.contextaware.transport.servlet;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 18, 2010
 * Time: 8:30:40 PM
 */
public class PingServletImplTest {
    private PingServletImpl pingServlet;

    private HttpServletRequest mockedRequest;
    private HttpServletResponse mockedResponse;

    @Before
    public void setup() {
        mockedRequest = mock(HttpServletRequest.class);
        mockedResponse = mock(HttpServletResponse.class);
        when(mockedRequest.getRemoteAddr()).thenReturn("JUnit test-address");

        pingServlet = new PingServletImpl();
    }

    @Test
    public void testPingServlet() {
        pingServlet.doGet(mockedRequest, mockedResponse);

        verify(mockedRequest).getRemoteAddr();
    }
}
