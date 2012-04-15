package ac.uk.brunel.cloudhomescreen.transport.servlet;

import ac.uk.brunel.cloudhomescreen.constants.HomeScreenConstants;
import ac.uk.brunel.cloudhomescreen.dto.DeviceId;
import ac.uk.brunel.cloudhomescreen.persistence.device.DeviceIdRegistrationDao;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:09:19 PM - Dec 5, 2010
 */
@Singleton
public class DeviceIdReceiver extends HttpServlet {
    private final static Logger logger = LoggerFactory.getLogger(DeviceIdReceiver.class);
    private final Provider<DeviceIdRegistrationDao> deviceIdDaoProvider;

    @Inject
    public DeviceIdReceiver(final Provider<DeviceIdRegistrationDao> deviceIdDaoProvider) {
        this.deviceIdDaoProvider = deviceIdDaoProvider;

        logger.info("Injecting provider");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        final String email = request.getParameter(HomeScreenConstants.URL_PARAM_EMAIL);
        final String deviceId = request.getParameter(HomeScreenConstants.URL_PARAM_DEVICEID);
        logger.info("Email received: " + email);
        logger.info("DeviceId received: " + deviceId);

        if (email != null && email.length() > 0 && deviceId != null && deviceId.length() > 0) {
            DeviceId deviceIdDto = new DeviceId(email, deviceId);

            deviceIdDaoProvider.get().persistDeviceId(deviceIdDto);
        } else {
            logger.warn("Some or all of the device data received was either null or empty");
        }
    }
}
