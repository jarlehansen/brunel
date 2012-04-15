package ac.uk.brunel.benchmark.server.sender.xmpp;

import com.google.appengine.api.xmpp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:46 PM - 11/22/11
 */
public class XmppSender {
    private static final Logger logger = LoggerFactory.getLogger(XmppSender.class);
    private static final XMPPService xmppService = XMPPServiceFactory.getXMPPService();

    static {
        //        JID ac.uk.brunel.benchmark.server.sender = new JID("hansjar@googlemail.com");
//        xmppService.sendInvitation(ac.uk.brunel.benchmark.server.sender);
//        JID sender2 = new JID("simplecloud2device@gmail.com");
//        xmppService.sendInvitation(sender2);
//        JID sender3 = new JID("simplecloud2device2@gmail.com");
//        xmppService.sendInvitation(sender3);
//        JID sender4 = new JID("brunel.nith@gmail.com");
//        xmppService.sendInvitation(sender4);
//        JID sender5 = new JID("brunel.nith2@gmail.com");
//        xmppService.sendInvitation(sender5);
    }

    public XmppSender() {
    }

    public void send(String email, String message) {
        JID sender = new JID(email);

        Message msgObj = new MessageBuilder().withRecipientJids(sender).withFromJid(null)
                .withMessageType(MessageType.NORMAL).withBody(message).build();

        SendResponse response = xmppService.sendMessage(msgObj);

        SendResponse.Status status = response.getStatusMap().get(sender);
        logger.info("StatusResponse: {}", status.toString());
    }
}
