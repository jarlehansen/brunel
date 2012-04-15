package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.contextaware.network.generated.UserListProtobuf;
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
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 4:35:28 PM
 */
@Singleton
public class MeetingUsersRegistration extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MeetingUsersRegistration.class);
    private final Provider<PresentationService> presentationServiceProvider;

    @Inject
    public MeetingUsersRegistration(final Provider<PresentationService> presentationServiceProvider) {
        this.presentationServiceProvider = presentationServiceProvider;
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        registerUser(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
        registerUser(request, response);
    }

    private void registerUser(final HttpServletRequest request, final HttpServletResponse response) {
        InputStream input = null;

        try {
            input = request.getInputStream();

            final PresentationService presentationService = presentationServiceProvider.get();            
            final UserListProtobuf.UserList userList = UserListProtobuf.UserList.parseFrom(input);
            if(logger.isDebugEnabled()) {
                logger.debug("registerUser input: " + userList);
            }

            final String meetingId = userList.getMeetingId();
            List<String> btAddresses = userList.getBtaddressList();

            for (String btAddress : btAddresses) {
                presentationService.registerConnectedUser(meetingId, btAddress);
            }
        } catch (IOException io) {
            if(logger.isErrorEnabled()) {
                logger.error("IOException when parsing the UserList from the input stream", io);
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
