package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.contextaware.network.generated.MeetingBtAddressesProtobuf;
import ac.uk.brunel.contextaware.network.generated.MeetingProtobuf;
import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.dto.MeetingNote;
import ac.uk.brunel.server.contextaware.service.PresentationService;
import ac.uk.brunel.server.contextaware.transport.servlet.factory.ProtobufObjectFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 12, 2010
 * Time: 3:19:41 PM
 */
@Singleton
public class MeetingRegistration extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MeetingRegistration.class);

    private final Provider<PresentationService> presentationServiceProvider;

    @Inject
    public MeetingRegistration(final Provider<PresentationService> presentationServiceProvider) {
        this.presentationServiceProvider = presentationServiceProvider;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        registerMeeting(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        registerMeeting(request, response);
    }

    private void registerMeeting(final HttpServletRequest request, final HttpServletResponse response) {
        InputStream input = null;

        try {
            input = request.getInputStream();

            MeetingProtobuf.Meeting protoMeeting = MeetingProtobuf.Meeting.parseFrom(input);
            if (logger.isDebugEnabled()) {
                logger.debug("registerMeeting input: " + protoMeeting.toString());
            }

            Meeting meeting = ProtobufObjectFactory.createDtoMeeting(protoMeeting);

            PresentationService presentationService = presentationServiceProvider.get();
            MeetingNote meetingNote = presentationService.registerStartedMeeting(meeting);
            sendMeetingInfoToStream(meetingNote, response);
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("IOException when trying to read the MeetingProtobuf object from the input stream", io);
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException io) {
                }
            }
        }
    }

    private void sendMeetingInfoToStream(final MeetingNote meetingNote, final HttpServletResponse response) {
        MeetingBtAddressesProtobuf.MeetingBtAddresses meetingBtAddresses = ProtobufObjectFactory.createMeetingBtAddresses(meetingNote);

        if (logger.isDebugEnabled()) {
            logger.debug("Returning meetingBtAddresses: " + meetingBtAddresses.toString());
        }

        OutputStream output = null;
        try {
            output = response.getOutputStream();
            meetingBtAddresses.writeTo(output);
            output.flush();
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("IOException when trying to write the MeetingBtAddresses object to the stream, " + meetingBtAddresses, io);
            }
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException io) {
                }
            }
        }
    }
}
