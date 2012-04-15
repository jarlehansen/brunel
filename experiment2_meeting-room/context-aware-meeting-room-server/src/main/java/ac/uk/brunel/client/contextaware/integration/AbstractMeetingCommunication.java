package ac.uk.brunel.client.contextaware.integration;

import ac.uk.brunel.client.contextaware.properties.PropertiesReader;
import com.google.protobuf.Message;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import java.io.IOException;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 16, 2010
 * Time: 8:47:19 PM
 */
public abstract class AbstractMeetingCommunication {
    private final HttpClient client;
    protected final String serviceAddress;

    protected AbstractMeetingCommunication(final HttpClient client, final String servicePropertyKey) {
        this.client = client;
        this.serviceAddress = PropertiesReader.SERVER_APP.getCloudServiceName(servicePropertyKey);
    }

    protected HttpResponse sendProtoMessage(final Message protoMessage) throws IOException {
        HttpEntity content = new ByteArrayEntity(protoMessage.toByteArray());

        HttpPost postMethod = new HttpPost(serviceAddress);
        postMethod.setEntity(content);
        
        return client.execute(postMethod);
    }
}
