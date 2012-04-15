package ac.uk.brunel.client.contextaware.integration.presenter;

import ac.uk.brunel.client.contextaware.annotation.LoggingAspect;
import ac.uk.brunel.client.contextaware.integration.AbstractMeetingCommunication;
import ac.uk.brunel.client.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.contextaware.network.generated.PresenterEventProtobuf;
import com.google.inject.Inject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 28, 2010
 * Time: 4:11:48 PM
 */
@LoggingAspect
public class PresenterEventSenderImpl extends AbstractMeetingCommunication implements PresenterEventSender {
    private static final Logger logger = LoggerFactory.getLogger(PresenterEventSenderImpl.class);

    @Inject
    public PresenterEventSenderImpl(final HttpClient client) {
        super(client, PropertiesConstants.SERVER_CLOUD_SERVICE_PRESENTEREVENT);
    }

    public void sendPresenterEvent(final String meetingId, final int currentSlideNumber) {
        if (logger.isDebugEnabled()) {
            logger.debug("Trying to send meetingId: " + meetingId + " and currentSlideNumber: " + currentSlideNumber + " to " + serviceAddress);
        }

        if (meetingId != null && currentSlideNumber > 0) {
            PresenterEventProtobuf.PresenterEvent presenterEvent = PresenterEventProtobuf.PresenterEvent.newBuilder().setMeetingId(meetingId)
                    .setSlideNumber(currentSlideNumber).build();

            HttpEntity responseEntity = null;
            try {
                HttpResponse response = sendProtoMessage(presenterEvent);
                responseEntity = response.getEntity();
            } catch (IOException io) {
                if (logger.isErrorEnabled()) {
                    logger.error("IOException when trying to send the PresenterEvent object to " + serviceAddress, io);
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
}
