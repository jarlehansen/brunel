package ac.uk.brunel.benchmark.client.task.c2dm;

import ac.uk.brunel.benchmark.client.TaskStateApplication;
import ac.uk.brunel.benchmark.client.UserInfo;
import ac.uk.brunel.benchmark.client.task.AbstractCallback;
import android.content.Context;
import com.googlecode.sc2dm.PushMessages;
import com.googlecode.sc2dm.SC2DMCallback;
import com.googlecode.sc2dm.SC2DMDirectMessages;
import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:04 PM - 11/21/11
 */
public class C2DMCallback extends AbstractCallback implements SC2DMCallback {
    private String registrationId;
    private int counter = 0;

    public C2DMCallback(TaskStateApplication taskState) {
        super(taskState);
    }

    @Override
    protected void sendMessage() {
        SC2DMLogger.i("C2DM start ", servletAction.name());
        servletAction.send(getTech(), UserInfo.EMAIL, registrationId);
    }

    @Override
    protected String getTech() {
        return "C2DM";
    }

    public void onRegistered(Context context, String registrationId) {
        SC2DMLogger.i("C2DM registered, regId: ", registrationId);
        this.registrationId = registrationId;
        taskState.setC2DMRegistered("Registered");
    }

    public void onMessage(Context context, String message) {
        SC2DMLogger.i("C2DM message received ", servletAction.type());
        super.sendResult();
        taskState.increaseC2DM();
    }

    public void onError(Context context, String error) {
        SC2DMLogger.e("C2DM error ", error);
        taskState.setC2DMRegistered(error);

        try {
            Thread.sleep(1000 * counter);
        } catch (InterruptedException ie) {
        }

        if (counter <= 5) {
            PushMessages pushMessages = new SC2DMDirectMessages(context, "hansjar@gmail.com", this);
            pushMessages.enable();
            counter++;
        }
    }
}
