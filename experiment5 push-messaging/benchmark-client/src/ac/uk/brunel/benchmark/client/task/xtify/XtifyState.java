package ac.uk.brunel.benchmark.client.task.xtify;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 9:30 PM - 11/24/11
 */
public enum XtifyState {
    INSTANCE;

    private static XtifyCallback xtifyCallback;
    private static String userKey;

    public void setXtifyCallback(XtifyCallback xtifyCallback) {
        XtifyState.xtifyCallback = xtifyCallback;
    }

    public void setUserKey(String userKey) {
        XtifyState.userKey = userKey;
    }

    public XtifyCallback xtifyCallback() {
        return xtifyCallback;
    }

    public String userKey() {
        return userKey;
    }
}
