package ac.uk.brunel.server.contextaware.transport.servlet.util;

import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.mockito.Mockito.when;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 10:11:42 PM
 */
public enum ServletMockUtil {
    ;

    private static final String folderName = "build/temp-files";
    private static final File folder = new File(folderName);
    private static final File outFile = new File(folderName + "/test2.txt");
    private static final File inFile = new File(folderName + "/test.txt");

    private static void createInputFile() throws IOException {
        createFolder();
        boolean inFileCreated = inFile.createNewFile();
        System.out.println("Input file created: " + inFileCreated);
    }

    private static void createOutputFile() throws IOException {
        createFolder();
        boolean outFileCreated = outFile.createNewFile();
        System.out.println("Output file created: " + outFileCreated);
    }

    private static void createFolder() {
        boolean folderCreated = folder.mkdirs();
        System.out.println("Folder created: " + folderCreated);
    }

    public static ServletInputStreamTester createServletInput(final HttpServletRequest mockedServletRequest) throws IOException {
        createInputFile();
        final ServletInputStreamTester servletInputStreamTester = new ServletInputStreamTester(new FileInputStream(inFile));
        when(mockedServletRequest.getInputStream()).thenReturn(servletInputStreamTester);

        return servletInputStreamTester;
    }

    public static ServletOutputStreamTester createServletOutput(final HttpServletResponse mockedServletResponse) throws IOException {
        createOutputFile();
        final ServletOutputStreamTester servletOutputStreamTester = new ServletOutputStreamTester(new FileOutputStream(outFile));
        when(mockedServletResponse.getOutputStream()).thenReturn(servletOutputStreamTester);

        return servletOutputStreamTester;
    }

    public static FileOutputStream createTestOutputFile() throws IOException {
        return new FileOutputStream(inFile);
    }

    public static HttpServletRequest createRequestMock() {
        return Mockito.mock(HttpServletRequest.class);
    }

    public static HttpServletResponse createResponseMock() {
        return Mockito.mock(HttpServletResponse.class);
    }
}
