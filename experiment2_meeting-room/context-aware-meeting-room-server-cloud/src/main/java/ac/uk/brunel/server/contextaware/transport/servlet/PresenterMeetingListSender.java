package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.contextaware.network.generated.MeetingProtobuf;
import ac.uk.brunel.contextaware.network.generated.PresenterDetailsProtobuf;
import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.service.MeetingService;
import ac.uk.brunel.server.contextaware.transport.servlet.factory.ProtobufObjectFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 8:35:49 PM
 */
@Singleton
public class PresenterMeetingListSender extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PresenterMeetingListSender.class);

    private final MeetingService meetingService;

    @Inject
    public PresenterMeetingListSender(final MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        getUserMeetings(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        getUserMeetings(request, response);
    }

    private void getUserMeetings(final HttpServletRequest request, final HttpServletResponse response) {
        InputStream input = null;
        OutputStream output = null;

        try {
            input = request.getInputStream();
            PresenterDetailsProtobuf.PresenterDetails presenterDetails = PresenterDetailsProtobuf.PresenterDetails.parseFrom(input);
            if (logger.isDebugEnabled()) {
                logger.debug("getUserMeetings input: " + presenterDetails.toString());
            }

            final String email = presenterDetails.getEmail();

            if (email != null && email.length() > 0) {
                output = response.getOutputStream();

                List<Meeting> dtoMeetingList = meetingService.getTodaysPresenterMeetings(presenterDetails.getEmail());

                MeetingProtobuf.MeetingList protoMeetingList = ProtobufObjectFactory.createProtoMeetingList(dtoMeetingList);
                protoMeetingList.writeTo(output);
            }
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("The servlet PresenterMeetingListSender is unable to send or receive ProtocolBuffer message", io);
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException io) {
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException io) {
                }
            }
        }
    }
}
