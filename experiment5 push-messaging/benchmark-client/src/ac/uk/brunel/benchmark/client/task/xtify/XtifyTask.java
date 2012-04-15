package ac.uk.brunel.benchmark.client.task.xtify;

import ac.uk.brunel.benchmark.client.TaskStateApplication;
import android.content.Context;
import com.googlecode.sc2dm.log.SC2DMLogger;
import com.xtify.android.sdk.PersistentLocationManager;

import java.util.TimerTask;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:28 PM - 11/24/11
 */
public class XtifyTask extends TimerTask {

    public XtifyTask(Context context) {
        PersistentLocationManager persistentLocationManager = new PersistentLocationManager(context);
        persistentLocationManager.startService();

//        persistentLocationManager.setNotificationsDelivery(false);
        XtifyState.INSTANCE.setUserKey(persistentLocationManager.getUserKey());

        SC2DMLogger.i("Xtify connected, user key: ", persistentLocationManager.getUserKey());
        XtifyState.INSTANCE.setXtifyCallback(new XtifyCallback((TaskStateApplication) context.getApplicationContext()));
    }

    @Override
    public void run() {
        XtifyState.INSTANCE.xtifyCallback().startMessages();
    }

    @Override
    public String toString() {
        return "Xtify";
    }
}
