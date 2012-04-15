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
public class LargeMessageSenderServlet extends AbstractMessageSenderServlet {
    private static final Logger logger = LoggerFactory.getLogger(LargeMessageSenderServlet.class);

    private static String message = "01234567891011121314151617181920212223242526272829303132333435363738394041424344454" +
            "64748495051525354555657585960616263646566676869707172737475767778798081828384858687888990919293949596979899" +
            "10010110210310410510610710810911011111211311411511611711811912012112212312412512612712812913013113213313413" +
            "51361371381391401411421431441451461471481491501511521531541551561571581591601611621631641651661671681691701" +
            "71172173174175176177178179180181182183184185186187188189190191192193194195196197198199200201202203204205206" +
            "20720820921021121221321421521621721821922022122222322422522622722822923023123223323423523623723823924024124" +
            "22432442452462472482492502512522532542552562572582592602612622632642652662672682692702712722732742752762772" +
            "78279280281282283284285286287288289290291292293294295296297298299300301302303304305306307308309310311312313" +
            "31431531631731831932032132232332432532632732832933033133233333433531";

    static {
        logger.info("Large message size: {}", message.getBytes().length);
    }

    @Inject
    public LargeMessageSenderServlet(Provider<MessageSenderFacade> messageSenderFacadeProvider) {
        super(messageSenderFacadeProvider);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        super.populateFields(request);

        if (validInput()) {
            logger.info("Sending large message to: {}", email);
            super.sendMessage(message);
        }
    }
}
