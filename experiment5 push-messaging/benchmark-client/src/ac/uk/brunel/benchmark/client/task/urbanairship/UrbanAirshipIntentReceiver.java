package ac.uk.brunel.benchmark.client.task.urbanairship;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.googlecode.sc2dm.log.SC2DMLogger;
import com.urbanairship.push.PushManager;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 4:42 PM - 11/24/11
 */
public class UrbanAirshipIntentReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(PushManager.ACTION_PUSH_RECEIVED)) {
            UrbanAirshipState.INSTANCE.urbanAirshipCallback().sendResult();
            UrbanAirshipState.INSTANCE.urbanAirshipCallback().increaseUA();
            SC2DMLogger.i("Urban Airship message received ", intent.getStringExtra(PushManager.EXTRA_ALERT));
        } else if (action.equals(PushManager.ACTION_REGISTRATION_FINISHED)) {
            String pushId = intent.getStringExtra(PushManager.EXTRA_APID);
            UrbanAirshipState.INSTANCE.setPushId(pushId);
        }
    }


}
