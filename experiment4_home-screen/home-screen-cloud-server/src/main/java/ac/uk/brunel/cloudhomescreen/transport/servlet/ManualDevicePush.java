package ac.uk.brunel.cloudhomescreen.transport.servlet;

import ac.uk.brunel.cloudhomescreen.constants.HomeScreenConstants;
import ac.uk.brunel.cloudhomescreen.service.ManualPushService;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:02 PM - 1/9/11
 */
@Singleton
public class ManualDevicePush extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(ManualDevicePush.class);

    private final Provider<ManualPushService> manualPushServiceProvider;

    @Inject
    public ManualDevicePush(final Provider<ManualPushService> manualPushServiceProvider) {
        this.manualPushServiceProvider = manualPushServiceProvider;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(HomeScreenConstants.URL_PARAM_EMAIL);
        String type = request.getParameter(HomeScreenConstants.URL_PARAM_TYPE);

        logger.info("ManualDevicePush request received, email: " + email + ", type: " + type);

        String message = manualPushServiceProvider.get().getPersistedUserInfo(type, email);

        if(message != null && message.length() > 0) {
            sendResponse(message, response);
        }
    }

    private void sendResponse(final String message, final HttpServletResponse response) {
        logger.info("Sending message: " + message);

        OutputStream outputStream = null;
        DataOutputStream dataOutputStream = null;

        try {
            outputStream = response.getOutputStream();
            dataOutputStream = new DataOutputStream(response.getOutputStream());
            dataOutputStream.writeUTF(message);
        } catch (IOException io) {
            logger.error("Unable to send manual device push response", io);
        } finally {
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException io) {
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException io) {
                }
            }
        }
    }
}
