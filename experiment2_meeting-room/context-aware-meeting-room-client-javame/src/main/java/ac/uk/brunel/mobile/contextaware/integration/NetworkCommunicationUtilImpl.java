package ac.uk.brunel.mobile.contextaware.integration;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class NetworkCommunicationUtilImpl implements NetworkCommunicationUtil {

	public NetworkCommunicationUtilImpl() {
	}

	public HttpConnection createConnector(final String serverAddress) throws IOException {
		final HttpConnection connection = (HttpConnection) Connector.open(serverAddress);
		connection.setRequestMethod(HttpConnection.POST);
		connection.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
		
		return connection;
	}
}
