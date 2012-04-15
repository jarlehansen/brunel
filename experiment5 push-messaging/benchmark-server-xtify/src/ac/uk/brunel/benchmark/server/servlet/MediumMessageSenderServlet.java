package ac.uk.brunel.benchmark.server.servlet;

import ac.uk.brunel.benchmark.server.sender.MessageSenderFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:21 PM - 11/21/11
 */
@Singleton
public class MediumMessageSenderServlet extends AbstractMessageSenderServlet {
    private static final Logger logger = LoggerFactory.getLogger(MediumMessageSenderServlet.class);

    private static String message = "01234567891011121314151617181920212223242526272829303132333435363738394041424344454" +
            "64748495051525354555657585960616263646566676869707172737475767778798081828384858687888990919293949596979899" +
            "10010110210310410510610710810911011111211311411511611711811912012112212312412512612712812913013113213313413" +
            "51361371381391401411421431441451461471481491501511521531541551561571581591601611621631641651661671681691701" +
            "7117217317417517617717817918018118218318418518";

    static {
        logger.info("Medium message size: {}", message.getBytes().length);
    }

    @Inject
    public MediumMessageSenderServlet(Provider<MessageSenderFacade> messageSenderFacadeProvider) {
        super(messageSenderFacadeProvider);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        super.populateFields(request);

        if (validInput()) {
            logger.info("Sending medium message to: {} - {}", email, regId);
            super.sendMessage(message);
        }
    }
}
