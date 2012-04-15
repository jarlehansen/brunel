package ac.uk.brunel.benchmark.client.battery;

import ac.uk.brunel.benchmark.client.server.ServletAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:54 PM - 2/25/12
 */
public class BatteryLevel extends BroadcastReceiver {
    private final String tech;

    private int previousBatteryLevel = 100;

    public BatteryLevel(String tech) {
        this.tech = tech;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int level = -1;

        if (rawlevel >= 0 && scale > 0) {
            level = (rawlevel * 100) / scale;
        }

        if (level != previousBatteryLevel) {
            previousBatteryLevel = level;
            ServletAction.sendBatteryRequest(tech, rawlevel, previousBatteryLevel);
        }
    }
}
