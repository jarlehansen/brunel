package ac.uk.brunel.benchmark.server.servlet;

import ac.uk.brunel.benchmark.server.persistence.GoogleAuthDao;
import ac.uk.brunel.benchmark.server.sender.MessageSenderFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:21 PM - 11/21/11
 */
@Singleton
public class SmallMessageSenderServlet extends AbstractMessageSenderServlet {
    private static final Logger logger = LoggerFactory.getLogger(SmallMessageSenderServlet.class);

    private static final String message = "1";

    static {
        logger.info("Small message size: {}", message.getBytes().length);
    }

    @Inject
    public SmallMessageSenderServlet(Provider<MessageSenderFacade> messageSenderFacadeProvider) {
        super(messageSenderFacadeProvider);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.populateFields(request);

        if (validInput()) {
            logger.info("Sending small message to: {}", email);
            super.sendMessage(message);
        }
    }
}
