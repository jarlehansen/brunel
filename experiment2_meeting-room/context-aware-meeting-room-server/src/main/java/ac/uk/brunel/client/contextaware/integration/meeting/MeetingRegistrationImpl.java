package ac.uk.brunel.client.contextaware.integration.meeting;

import ac.uk.brunel.client.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.client.contextaware.dto.Meeting;
import ac.uk.brunel.client.contextaware.integration.AbstractMeetingCommunication;
import ac.uk.brunel.client.contextaware.integration.factory.ProtobufObjectFactory;
import ac.uk.brunel.client.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.contextaware.network.generated.MeetingBtAddressesProtobuf;
import ac.uk.brunel.contextaware.network.generated.MeetingProtobuf;
import com.google.inject.Inject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 12, 2010
 * Time: 3:17:57 PM
 */
@LoggingAspect
public class MeetingRegistrationImpl extends AbstractMeetingCommunication implements MeetingRegistration {
    private final static Logger logger = LoggerFactory.getLogger(MeetingRegistrationImpl.class);

    @Inject
    public MeetingRegistrationImpl(final HttpClient client) {
        super(client, PropertiesConstants.SERVER_CLOUD_SERVICE_MEETINGREGISTRATION);
    }

    public List<String> registerMeetingAndGetRegisteredParticipants(final Meeting meeting) {
        List<String> registeredBluetoothAddresses = Collections.emptyList();

        MeetingProtobuf.Meeting protoMeeting = ProtobufObjectFactory.createProtoMeeting(meeting);

        try {
            HttpResponse response = sendProtoMessage(protoMeeting);
            HttpEntity responseEntity = response.getEntity();
            registeredBluetoothAddresses = getRegisteredParticipants(responseEntity);
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("Problem executing POST method for url: " + serviceAddress, io);
            }
        }

        return registeredBluetoothAddresses;
    }

    private List<String> getRegisteredParticipants(final HttpEntity responseEntity) {
        List<String> registeredBluetoothAddresses = new ArrayList<String>();

        InputStream input = null;
        try {
            input = responseEntity.getContent();

            if (input != null) {
                MeetingBtAddressesProtobuf.MeetingBtAddresses meetingBtAddresses = MeetingBtAddressesProtobuf.MeetingBtAddresses.parseFrom(input);

                String btAddresses = meetingBtAddresses.getBluetoothAddresses();
                String[] btAddressesArgs = btAddresses.split(";");

                if (btAddressesArgs.length > 0 && !"".equals(btAddressesArgs[0])) {
                    registeredBluetoothAddresses.addAll(Arrays.asList(btAddressesArgs));
                }
            }
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("IOException when trying to read the MeetingBtAddresses object from the input stream", io);
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException io) {
                }
            }

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

        return registeredBluetoothAddresses;
    }
}
