package ac.uk.brunel.benchmark.server.sender;

import ac.uk.brunel.benchmark.server.persistence.GoogleAuthDao;
import ac.uk.brunel.benchmark.server.sender.c2dm.C2DMSender;
import ac.uk.brunel.benchmark.server.sender.ua.UrbanAirshipSender;
import ac.uk.brunel.benchmark.server.sender.xmpp.XmppSender;

import javax.inject.Inject;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:37 PM - 11/22/11
 */
public class MessageSenderFacadeImpl implements MessageSenderFacade {
    private final GoogleAuthDao googleAuthDao;

    @Inject
    public MessageSenderFacadeImpl(GoogleAuthDao googleAuthDao) {
        this.googleAuthDao = googleAuthDao;
    }

    @Override
    public void sendMessage(PushMessage pushMessage) {
        String tech = pushMessage.getTech();

        if (tech.equals("C2DM")) {
            //String token = googleAuthDao.getAuthToken();
            new C2DMSender().send("", pushMessage.getMessage(), pushMessage.getRegId());
        } else if (tech.equals("XMPP")) {
            new XmppSender().send(pushMessage.getEmail(), pushMessage.getMessage());
        } else if (tech.equals("UAir")) {
            new UrbanAirshipSender().send(pushMessage.getMessage(), pushMessage.getRegId());
        } else if(tech.equals("Xtif")) {
            
        }
    }
}
