package ac.uk.brunel.benchmark.client.task.urbanairship;

import ac.uk.brunel.benchmark.client.TaskStateApplication;
import ac.uk.brunel.benchmark.client.UserInfo;
import ac.uk.brunel.benchmark.client.task.AbstractCallback;
import android.content.Context;
import com.googlecode.sc2dm.log.SC2DMLogger;
import com.urbanairship.push.PushManager;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:27 PM - 11/24/11
 */
public class UrbanAirshipCallback extends AbstractCallback {

    public UrbanAirshipCallback(Context context) {
        super((TaskStateApplication) context.getApplicationContext());

        String pushId = PushManager.shared().getPreferences().getPushId();
        if (pushId != null && !"".equals(pushId))
            UrbanAirshipState.INSTANCE.setPushId(pushId);

        taskState.setUaRegId();
        SC2DMLogger.i("Urban Airship connected");
    }

    @Override
    protected void sendMessage() {
        SC2DMLogger.i("Urban Airship start ", servletAction.name());
        servletAction.send(getTech(), UserInfo.EMAIL, UrbanAirshipState.INSTANCE.pushId());
    }

    public void increaseUA() {
        taskState.increaseUA();
    }

    @Override
    protected String getTech() {
        return "UAir";
    }
}
