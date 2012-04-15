package ac.uk.brunel.server.contextaware.transport.servlet.util;

import javax.servlet.ServletOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 9:37:02 PM
 */
public class ServletOutputStreamTester extends ServletOutputStream {
    private final FileOutputStream fileOutputStream;
    private int counter = 0;
    private boolean closed = false;

    public ServletOutputStreamTester(final FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
    }

    @Override
    public void write(int b) throws IOException {
        fileOutputStream.write(b);
        counter++;
    }

    @Override
    public void write(byte[] b) throws IOException {
        fileOutputStream.write(b);
        counter++;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        fileOutputStream.write(b, off, len);
        counter++;
    }

    @Override
    public void flush() throws IOException {
        fileOutputStream.flush();
        counter++;
    }

    @Override
    public void close() throws IOException {
        fileOutputStream.close();
        closed = true;
    }

    public int getCounter() {
        return counter;
    }

    public boolean closed() {
        return closed;
    }
}
