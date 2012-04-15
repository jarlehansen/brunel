package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.contextaware.network.generated.PresenterEventProtobuf;
import ac.uk.brunel.server.contextaware.service.PresentationService;
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

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 30, 2010
 * Time: 10:26:04 PM
 */
@Singleton
public class MeetingUpdater extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MeetingUpdater.class);

    private final Provider<PresentationService> presentationServiceProvider;

    @Inject
    public MeetingUpdater(final Provider<PresentationService> presentationServiceProvider) {
        this.presentationServiceProvider = presentationServiceProvider;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        updateMeeting(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        updateMeeting(request, response);
    }

    private void updateMeeting(final HttpServletRequest request, final HttpServletResponse response) {
        InputStream input = null;

        try {
            input = request.getInputStream();
            PresenterEventProtobuf.PresenterEvent presenterEvent = PresenterEventProtobuf.PresenterEvent.parseFrom(input);

            PresentationService presentationService = presentationServiceProvider.get();
            presentationService.updateCurrentSlideNumber(presenterEvent.getMeetingId(), presenterEvent.getSlideNumber());
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("IOException when parsing the PresenterEvent from the input stream", io);
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
}
