package ac.uk.brunel.mobile.contextaware.integration;

import java.io.IOException;

import javax.microedition.io.HttpConnection;

public interface NetworkCommunicationUtil {
	public HttpConnection createConnector(final String serverAddress) throws IOException;
}
