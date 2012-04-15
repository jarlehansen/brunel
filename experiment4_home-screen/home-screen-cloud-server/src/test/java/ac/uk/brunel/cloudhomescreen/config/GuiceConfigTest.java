package ac.uk.brunel.cloudhomescreen.config;

import ac.uk.brunel.cloudhomescreen.integration.AppEngineAuthentication;
import ac.uk.brunel.cloudhomescreen.persistence.configuration.UserConfigurationDao;
import ac.uk.brunel.cloudhomescreen.persistence.device.DeviceIdRegistrationDao;
import ac.uk.brunel.cloudhomescreen.service.UserConfigurationService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:05 PM - 1/9/11
 */
public class GuiceConfigTest {

    @Test
    public void testGuiceConfig() {
        Injector injector = Guice.createInjector(new HomeScreenModule(), new WebRegistrationModule());
    
        AppEngineAuthentication appEngineAuthentication = injector.getInstance(AppEngineAuthentication.class);
        UserConfigurationService userConfigurationService = injector.getInstance(UserConfigurationService.class);
        UserConfigurationDao userConfigurationDao = injector.getInstance(UserConfigurationDao.class);

        DeviceIdRegistrationDao deviceIdRegistrationDao = injector.getInstance(DeviceIdRegistrationDao.class);
    }
}
