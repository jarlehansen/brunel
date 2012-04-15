package ac.uk.brunel.benchmark.client.task.urbanairship;

import android.content.Context;

import java.util.TimerTask;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:27 PM - 11/24/11
 */
public class UrbanAirshipTask extends TimerTask {

    public UrbanAirshipTask(Context context) {
        UrbanAirshipState.INSTANCE.setUrbanAirshipCallback(new UrbanAirshipCallback(context));
    }

    @Override
    public void run() {
        UrbanAirshipState.INSTANCE.urbanAirshipCallback().startMessages();
    }

    @Override
    public String toString() {
        return "UrbanAirship";
    }
}
