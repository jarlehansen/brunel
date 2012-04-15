package ac.uk.brunel.benchmark.client.task.c2dm;

import ac.uk.brunel.benchmark.client.TaskStateApplication;
import android.content.Context;
import com.googlecode.sc2dm.PushMessages;
import com.googlecode.sc2dm.SC2DMDirectMessages;

import java.util.TimerTask;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:04 PM - 11/21/11
 */
public class C2DMTask extends TimerTask {
    private final C2DMCallback c2dmCallback;

    public C2DMTask(Context context) {
        c2dmCallback = new C2DMCallback((TaskStateApplication) context.getApplicationContext());

        PushMessages pushMessages = new SC2DMDirectMessages(context, "hansjar@gmail.com", c2dmCallback);
        pushMessages.enable();
    }

    @Override
    public void run() {
        c2dmCallback.startMessages();
    }

    @Override
    public String toString() {
        return c2dmCallback.getTech();
    }
}
