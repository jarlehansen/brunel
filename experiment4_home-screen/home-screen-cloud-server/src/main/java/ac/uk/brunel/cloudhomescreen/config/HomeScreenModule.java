package ac.uk.brunel.cloudhomescreen.config;

import ac.uk.brunel.cloudhomescreen.integration.AppEngineAuthentication;
import ac.uk.brunel.cloudhomescreen.integration.AppEngineAuthenticationImpl;
import ac.uk.brunel.cloudhomescreen.integration.Cloud2DevicePush;
import ac.uk.brunel.cloudhomescreen.integration.Cloud2DevicePushImpl;
import ac.uk.brunel.cloudhomescreen.persistence.auth.AuthTokenDao;
import ac.uk.brunel.cloudhomescreen.persistence.auth.AuthTokenDaoImpl;
import ac.uk.brunel.cloudhomescreen.persistence.configuration.UserConfigurationDao;
import ac.uk.brunel.cloudhomescreen.persistence.configuration.UserConfigurationDaoImpl;
import ac.uk.brunel.cloudhomescreen.persistence.device.DeviceIdDaoImpl;
import ac.uk.brunel.cloudhomescreen.persistence.device.DeviceIdRetrieverDao;
import ac.uk.brunel.cloudhomescreen.service.UserConfigurationService;
import ac.uk.brunel.cloudhomescreen.service.UserConfigurationServiceImpl;
import com.google.inject.AbstractModule;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 1:54:15 PM - Nov 20, 2010
 */
public class HomeScreenModule extends AbstractModule {

    @Override
    protected void configure() {
        // Integration
        bind(AppEngineAuthentication.class).to(AppEngineAuthenticationImpl.class);
        bind(Cloud2DevicePush.class).to(Cloud2DevicePushImpl.class);

        // Service
        bind(UserConfigurationService.class).to(UserConfigurationServiceImpl.class);

        // Persistence
        bind(UserConfigurationDao.class).to(UserConfigurationDaoImpl.class);
        bind(DeviceIdRetrieverDao.class).to(DeviceIdDaoImpl.class);
        bind(AuthTokenDao.class).to(AuthTokenDaoImpl.class);
    }
}
