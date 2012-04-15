package com.googlecode.sc2dm.manifest.generator.servlet;

import com.googlecode.sc2dm.manifest.generator.service.GeneratedXml;
import com.googlecode.sc2dm.manifest.generator.service.ManifestGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:45 PM - 9/26/11
 */
@RunWith(MockitoJUnitRunner.class)
public class ManifestGeneratorTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private ManifestGenerator manifestGenerator;

    @Before
    public void setUp() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        manifestGenerator = new ManifestGenerator(null, "");
    }

    @Test
    public void doGet_nullInput() throws IOException, ServletException {
        manifestGenerator.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doGet_invalidPackage() throws IOException, ServletException {
        when(request.getParameter("packageName")).thenReturn("com-test");

        manifestGenerator.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doGet_validPackage() throws IOException, ServletException {
        GeneratedXml generatedXml = new GeneratedXml("xmlPersmissions", "xmlReceiver", "classReceiver");

        ManifestGeneratorService manifestGeneratorService = mock(ManifestGeneratorService.class);
        when(manifestGeneratorService.generate("com.test")).thenReturn(generatedXml);
        manifestGenerator = new ManifestGenerator(manifestGeneratorService, "timestamp");

        when(request.getParameter("packageName")).thenReturn("com.test");

        manifestGenerator.doGet(request, response);

        verify(request).getRequestDispatcher("WEB-INF/jsp/ManifestGenerator.jsp?class=classReceiver&xmlpermissions=xmlPersmissions&xmlreceiver=xmlReceiver&errormsg=");
        verify(requestDispatcher).forward(request, response);
    }
}
