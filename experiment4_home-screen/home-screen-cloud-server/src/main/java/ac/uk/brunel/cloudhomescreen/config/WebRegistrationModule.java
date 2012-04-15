package ac.uk.brunel.cloudhomescreen.config;

import ac.uk.brunel.cloudhomescreen.persistence.configuration.UserConfigurationDao;
import ac.uk.brunel.cloudhomescreen.persistence.configuration.UserConfigurationDaoImpl;
import ac.uk.brunel.cloudhomescreen.persistence.configuration.UserConfigurationRetrieverDao;
import ac.uk.brunel.cloudhomescreen.persistence.device.DeviceIdRegistrationDao;
import ac.uk.brunel.cloudhomescreen.persistence.device.DeviceIdDaoImpl;
import ac.uk.brunel.cloudhomescreen.persistence.statistics.StatisticsDao;
import ac.uk.brunel.cloudhomescreen.persistence.statistics.StatisticsDaoImpl;
import ac.uk.brunel.cloudhomescreen.service.ManualPushService;
import ac.uk.brunel.cloudhomescreen.service.ManualPushServiceImpl;
import ac.uk.brunel.cloudhomescreen.transport.servlet.DeviceIdReceiver;
import ac.uk.brunel.cloudhomescreen.transport.servlet.ManualDevicePush;
import ac.uk.brunel.cloudhomescreen.transport.servlet.StatisticsReceiver;
import com.google.inject.servlet.ServletModule;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 9:36 PM - 1/7/11
 */
public class WebRegistrationModule extends ServletModule {

    @Override
    protected void configureServlets() {
        serve("/register/deviceIdReceiver").with(DeviceIdReceiver.class);
        serve("/register/stats").with(StatisticsReceiver.class);
        serve("/register/manualDevicePush").with(ManualDevicePush.class);

        // Service
        bind(ManualPushService.class).to(ManualPushServiceImpl.class);

        // Persistence
        bind(DeviceIdRegistrationDao.class).to(DeviceIdDaoImpl.class);
        bind(StatisticsDao.class).to(StatisticsDaoImpl.class);
        bind(UserConfigurationRetrieverDao.class).to(UserConfigurationDaoImpl.class);
    }


}
