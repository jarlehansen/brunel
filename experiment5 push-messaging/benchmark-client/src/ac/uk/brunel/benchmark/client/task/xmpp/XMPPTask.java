package ac.uk.brunel.benchmark.client.task.xmpp;

import ac.uk.brunel.benchmark.client.TaskStateApplication;
import ac.uk.brunel.benchmark.client.UserInfo;
import android.content.Context;
import com.googlecode.sc2dm.log.SC2DMLogger;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;

import java.util.TimerTask;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:52 PM - 11/21/11
 */
public class XMPPTask extends TimerTask {
    private final Connection connection;
    private final XMPPCallback xmppCallback;
    private String email;

    public XMPPTask(Context context) {
        try {
            ConnectionConfiguration config = new ConnectionConfiguration("gmail.com");
            config.setSASLAuthenticationEnabled(true);

            connection = new XMPPConnection(config);
            connection.connect();

            email = UserInfo.EMAIL;
            if (email.contains("hansjar")) {
                email = "hansjar@googlemail.com";
            }
            connection.login(email, UserInfo.PASSWORD);
            SC2DMLogger.i("XMPP connected");

            PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class), new FromContainsFilter("benchmark-server@appspot.com"));
            xmppCallback = new XMPPCallback((TaskStateApplication) context.getApplicationContext(), email);
            connection.addPacketListener(xmppCallback, filter);
        } catch (XMPPException e) {
            SC2DMLogger.e("Unable to connect, ", e.getMessage());
            throw new IllegalStateException("Unable to connect to XMPP server", e);
        }
    }

    @Override
    public void run() {
        xmppCallback.startMessages();
    }

    @Override
    public String toString() {
        return xmppCallback.getTech();
    }
}
