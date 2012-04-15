package ac.uk.brunel.benchmark.client.task.urbanairship;

import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:31 PM - 11/24/11
 */
public enum UrbanAirshipState {
    INSTANCE;

    private static UrbanAirshipCallback urbanAirshipCallback = null;
    private static String pushId = null;

    public void setUrbanAirshipCallback(UrbanAirshipCallback urbanAirshipCallback) {
        UrbanAirshipState.urbanAirshipCallback = urbanAirshipCallback;
    }

    public UrbanAirshipCallback urbanAirshipCallback() {
        return UrbanAirshipState.urbanAirshipCallback;
    }

    public void setPushId(String pushId) {
        if (UrbanAirshipState.pushId == null) {
            SC2DMLogger.i("Urban Airship, storing pushId: ", pushId);
            UrbanAirshipState.pushId = pushId;
        }
    }

    public String pushId() {
        return UrbanAirshipState.pushId;
    }
}
