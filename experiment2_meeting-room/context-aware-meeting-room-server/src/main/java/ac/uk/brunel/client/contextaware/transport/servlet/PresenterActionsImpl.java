package ac.uk.brunel.client.contextaware.transport.servlet;

import ac.uk.brunel.client.contextaware.service.PresenterService;
import ac.uk.brunel.contextaware.network.generated.PresenterActionProtobuf;
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
 * Date: Feb 18, 2010
 * Time: 5:15:41 PM
 */
@Singleton
public class PresenterActionsImpl extends HttpServlet implements PresenterActions {
    private static final Logger logger = LoggerFactory.getLogger(PresenterActionsImpl.class);
    private final Provider<PresenterService> presenterServiceProvider;

    @Inject
    public PresenterActionsImpl(final Provider<PresenterService> presenterServiceProvider) {
        this.presenterServiceProvider = presenterServiceProvider;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        receivePresenterAction(request);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        receivePresenterAction(request);
    }

    public HttpServlet getServletObject() {
        return this;
    }

    private void receivePresenterAction(final HttpServletRequest request) {
        InputStream input = null;

        PresenterActionProtobuf.PresenterAction presenterAction = null;
        try {
            input = request.getInputStream();

            presenterAction = PresenterActionProtobuf.PresenterAction.parseFrom(input);
            if (logger.isDebugEnabled()) {
                logger.debug("presenterActions input: " + presenterAction.toString());
            }


        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("IOException when trying to read the PresenterAction object from the input stream", io);
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException io) {
                }
            }
        }

        if (presenterAction != null) {
            final PresenterService presenterService = presenterServiceProvider.get();
            presenterService.performPresenterAction(presenterAction);
        }
    }
}
