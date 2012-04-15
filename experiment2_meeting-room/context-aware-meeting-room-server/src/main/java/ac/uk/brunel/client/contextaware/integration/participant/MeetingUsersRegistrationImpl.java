package ac.uk.brunel.client.contextaware.integration.participant;

import ac.uk.brunel.client.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.client.contextaware.integration.AbstractMeetingCommunication;
import ac.uk.brunel.client.contextaware.integration.bluetooth.dto.BluetoothDevice;
import ac.uk.brunel.client.contextaware.integration.factory.ProtobufObjectFactory;
import ac.uk.brunel.client.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.contextaware.network.generated.UserListProtobuf;
import com.google.inject.Inject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 16, 2010
 * Time: 8:38:44 PM
 */
@LoggingAspect
public class MeetingUsersRegistrationImpl extends AbstractMeetingCommunication implements MeetingUsersRegistration {
    private static final Logger logger = LoggerFactory.getLogger(MeetingUsersRegistrationImpl.class);

    @Inject
    public MeetingUsersRegistrationImpl(final HttpClient client) {
        super(client, PropertiesConstants.SERVER_CLOUD_SERVICE_MEETINGUSERSREGISTRATION);
    }

    public void registerConnectedParticipants(final String meetingId, final List<BluetoothDevice> bluetoothDevices) {
        final UserListProtobuf.UserList protoUserList = ProtobufObjectFactory.createProtoUserList(meetingId, bluetoothDevices);

        HttpEntity responseEntity = null;
        try {
            HttpResponse response = sendProtoMessage(protoUserList);
            responseEntity = response.getEntity();
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("Problem executing POST method for url: " + serviceAddress, io);
            }
        } finally {
            if (responseEntity != null) {
                try {
                    /*
                    The name of this method is misnomer. It will be renamed to #finish() in the next major release.
                    This method is called to indicate that the content of this entity is no longer required.
                    All entity implementations are expected to release all allocated resources as a result of this method invocation.
                    Content streaming entities are also expected to dispose of the remaining content, if any.
                    Wrapping entities should delegate this call to the wrapped entity. 
                     */
                    responseEntity.consumeContent();
                } catch (IOException io) {
                }
            }
        }
    }
}
