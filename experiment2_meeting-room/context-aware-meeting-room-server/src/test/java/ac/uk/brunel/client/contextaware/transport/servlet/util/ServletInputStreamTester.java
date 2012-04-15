package ac.uk.brunel.client.contextaware.transport.servlet.util;

import javax.servlet.ServletInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 9:19:59 PM
 */
public class ServletInputStreamTester extends ServletInputStream {
    private final FileInputStream fileInputStream;
    private boolean closed = false;

    public ServletInputStreamTester(final FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return fileInputStream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return fileInputStream.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return fileInputStream.skip(n);
    }

    @Override
    public int available() throws IOException {
        return fileInputStream.available();
    }

    @Override
    public void close() throws IOException {
        fileInputStream.close();
        closed = true;
    }

    @Override
    public void mark(int readlimit) {
        fileInputStream.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        fileInputStream.reset();
    }

    @Override
    public boolean markSupported() {
        return fileInputStream.markSupported();
    }

    @Override
    public int read() throws IOException {
        return fileInputStream.read();
    }

    public boolean closed() {
        return closed;
    }
}
