package ac.uk.brunel.benchmark.client.task.xmpp;

import ac.uk.brunel.benchmark.client.TaskStateApplication;
import ac.uk.brunel.benchmark.client.task.AbstractCallback;
import com.googlecode.sc2dm.log.SC2DMLogger;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 11:17 PM - 11/23/11
 */
public class XMPPCallback extends AbstractCallback implements PacketListener {
    private String email;

    public XMPPCallback(TaskStateApplication taskState, String email) {
        super(taskState);
        this.email = email;
    }

    @Override
    protected void sendMessage() {
        SC2DMLogger.i("XMPP start ", servletAction.name());
        servletAction.send(getTech(), email);
    }

    @Override
    protected String getTech() {
        return "XMPP";
    }

    @Override
    public void processPacket(Packet packet) {
        if (packet instanceof Message) {
            SC2DMLogger.i("XMPP message received ", servletAction.type());
            super.sendResult();
            taskState.increaseXMPP();
//            Message messageObj = (Message) packet;
//            String msg = messageObj.getBody();
//            SC2DMLogger.i("Message: ", msg);
        }
    }


}
