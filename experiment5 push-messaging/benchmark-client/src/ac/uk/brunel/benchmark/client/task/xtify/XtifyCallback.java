package ac.uk.brunel.benchmark.client.task.xtify;

import ac.uk.brunel.benchmark.client.TaskStateApplication;
import ac.uk.brunel.benchmark.client.UserInfo;
import ac.uk.brunel.benchmark.client.task.AbstractCallback;
import com.googlecode.sc2dm.log.SC2DMLogger;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:28 PM - 11/24/11
 */
public class XtifyCallback extends AbstractCallback {

    public XtifyCallback(TaskStateApplication taskState) {
        super(taskState);
    }

    @Override
    protected void sendMessage() {
        SC2DMLogger.i("Xtify start ", servletAction.name());
        servletAction.send(getTech(), UserInfo.EMAIL, XtifyState.INSTANCE.userKey());
    }

    @Override
    protected String getTech() {
        return "Xtif";
    }

     public void increaseXtify() {
        taskState.increaseXtify();
    }
}
