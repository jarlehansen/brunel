package ac.uk.brunel.benchmark.client;

import ac.uk.brunel.benchmark.client.task.urbanairship.UrbanAirshipIntentReceiver;
import android.app.Application;
import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 12:32 PM - 11/24/11
 */
public class TaskStateApplication extends Application {
    private volatile String c2dmRegId = "";
    private volatile int c2dmMessages = 0;
    private volatile int xmppMessages = 0;
    private volatile int uaMessages = 0;
    private volatile String uaRegId = "";
    private volatile int xtifyMessages = 0;

    @Override
    public void onCreate() {
        super.onCreate();


        /* URBAN AIRSHIP */

        AirshipConfigOptions options = AirshipConfigOptions.loadDefaultOptions(getApplicationContext());
        options.developmentAppKey = "";
        options.developmentAppSecret = "";
        options.productionAppKey = "";
        options.productionAppSecret = "";

        options.c2dmSender = "";
        options.transport = "helium";
        options.inProduction = false;
        options.iapEnabled = false;

        UAirship.takeOff(this, options);
        PushManager.enablePush();
        PushManager.shared().getPreferences().setSoundEnabled(false);
        PushManager.shared().getPreferences().setVibrateEnabled(false);
        PushManager.shared().setIntentReceiver(UrbanAirshipIntentReceiver.class);
    }

    public void setC2DMRegistered(String regId) {
        c2dmRegId = regId;
    }

    public void increaseC2DM() {
        c2dmMessages++;
    }

    public void increaseXMPP() {
        xmppMessages++;
    }

    public void increaseUA() {
        uaMessages++;
    }

    public void setUaRegId() {
        uaRegId = "Registered";
    }

    public void increaseXtify() {
        xtifyMessages++;
    }

    public String getC2dmRegId() {
        return c2dmRegId;
    }

    public String getXmppMessages() {
        return Integer.toString(xmppMessages);
    }

    public String getC2dmMessages() {
        return Integer.toString(c2dmMessages);
    }

    public String getUAMessages() {
        return Integer.toString(uaMessages);
    }

    public String getUaRegId() {
        return uaRegId;
    }

    public String getXtifyMessages() {
        return Integer.toString(xtifyMessages);
    }
}
