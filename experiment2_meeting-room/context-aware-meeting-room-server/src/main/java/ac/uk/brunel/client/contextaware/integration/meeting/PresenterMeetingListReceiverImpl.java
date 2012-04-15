package ac.uk.brunel.client.contextaware.integration.meeting;

import ac.uk.brunel.client.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.client.contextaware.dto.Meeting;
import ac.uk.brunel.client.contextaware.integration.AbstractMeetingCommunication;
import ac.uk.brunel.client.contextaware.integration.factory.ProtobufObjectFactory;
import ac.uk.brunel.client.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.contextaware.network.generated.MeetingProtobuf;
import ac.uk.brunel.contextaware.network.generated.PresenterDetailsProtobuf;
import com.google.inject.Inject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 2, 2010
 * Time: 8:47:54 PM
 */
@LoggingAspect
public class PresenterMeetingListReceiverImpl extends AbstractMeetingCommunication implements PresenterMeetingListReceiver {
    private static final Logger logger = LoggerFactory.getLogger(PresenterMeetingListReceiverImpl.class);

    @Inject
    public PresenterMeetingListReceiverImpl(final HttpClient client) {
        super(client, PropertiesConstants.SERVER_CLOUD_SERVICE_PRESENTERMEETINGLIST);
    }

    public List<Meeting> getPresenterMeetingList(final String email) {
        List<Meeting> meetingList = Collections.emptyList();

        PresenterDetailsProtobuf.PresenterDetails presenterDetails = PresenterDetailsProtobuf.PresenterDetails.newBuilder().setEmail(email).build();

        try {
            HttpResponse response = sendProtoMessage(presenterDetails);
            HttpEntity responseEntity = response.getEntity();

            meetingList = getMeetingList(responseEntity);
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("Problem executing POST method for url:" + serviceAddress + ", email: " + email, io);
            }
        }

        return meetingList;
    }

    private List<Meeting> getMeetingList(final HttpEntity responseEntity) {
        List<Meeting> meetingList = new ArrayList<Meeting>();

        InputStream input = null;
        try {
            input = responseEntity.getContent();

            if (input != null) {
                MeetingProtobuf.MeetingList tempList = MeetingProtobuf.MeetingList.parseFrom(input);

                for (MeetingProtobuf.Meeting protoMeeting : tempList.getMeetingList()) {
                    meetingList.add(ProtobufObjectFactory.createDtoMeeting(protoMeeting));
                }
            }
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("IOException when trying to read the MeetingList object from the input stream", io);
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

        return meetingList;
    }
}