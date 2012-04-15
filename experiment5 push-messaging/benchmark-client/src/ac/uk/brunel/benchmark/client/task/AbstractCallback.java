package ac.uk.brunel.benchmark.client.task;

import ac.uk.brunel.benchmark.client.TaskStateApplication;
import ac.uk.brunel.benchmark.client.TimeUsed;
import ac.uk.brunel.benchmark.client.UserInfo;
import ac.uk.brunel.benchmark.client.server.ServletAction;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:49 AM - 11/24/11
 */
public abstract class AbstractCallback {
    protected final TaskStateApplication taskState;
    private int number = 0;

    protected ServletAction servletAction;
    protected TimeUsed timeUsed;

    public AbstractCallback(TaskStateApplication taskState) {
        this.taskState = taskState;
    }

    public void startMessages() {
        number++;
        servletAction = ServletAction.getType(number);
        if (servletAction != null) {
            timeUsed = new TimeUsed();
            sendMessage();
        } else {
            number = 0;
        }
    }

    public void sendResult() {
        long time = timeUsed.stop();
        ServletAction.RESULT.send(servletAction.type(), getTech(), UserInfo.EMAIL, time);

        startMessages();
    }

    protected abstract void sendMessage();

    protected abstract String getTech();

}
