package ac.uk.brunel.benchmark.client.task.xtify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 9:20 PM - 11/24/11
 */
public class XtifyIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("NOTIFICATION_DETAILS");
        if (msg != null && !"".equals(msg)) {

            XtifyState.INSTANCE.xtifyCallback().sendResult();
            XtifyState.INSTANCE.xtifyCallback().increaseXtify();
            SC2DMLogger.i("Message: ", msg);
        }
    }


}
