package ac.uk.brunel.cloudhomescreen.presentation.beans;

import ac.uk.brunel.cloudhomescreen.config.sources.ApplicationSources;
import ac.uk.brunel.cloudhomescreen.config.sources.PushMessageSource;
import ac.uk.brunel.cloudhomescreen.dto.UserConfiguration;
import ac.uk.brunel.cloudhomescreen.service.UserConfigurationService;
import com.google.inject.Inject;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:20:59 PM - Nov 20, 2010
 */
public class ConfigurationActionBean extends CommonActionBean {
    private final Logger logger = LoggerFactory.getLogger(ConfigurationActionBean.class);
    private static final String VIEW = "/WEB-INF/views/configuration_view.jsp";

    private final UserConfigurationService userConfigurationService;
    private UserConfiguration userConfiguration;

    @Inject
    public ConfigurationActionBean(final UserConfigurationService userConfigurationService) {
        this.userConfigurationService = userConfigurationService;
    }

    @Before(stages = LifecycleStage.BindingAndValidation)
    public void initUserConfiguration() {
        userConfiguration = userConfigurationService.getConfiguration(user);
    }

    @DefaultHandler
    public Resolution showRegisterConfigurationScreen() {
        return new ForwardResolution(VIEW);
    }

    public List<PushMessageSource> getPushSources() {
        return PushMessageSource.getAllSources();
    }

    public List<ApplicationSources> getApplicationSources() {
        return ApplicationSources.getAllSources();
    }

    public List<String> getApplications() {
        return userConfiguration.getApplications();
    }

    public void setApplications(final List<String> applications) {
        if (applications == null) {
            userConfiguration.setApplications(Collections.<String>emptyList());
        } else {
            userConfiguration.setApplications(applications);
        }
    }

    public List<String> getConfiguration() {
        return userConfiguration.getConfiguration();
    }

    public void setConfiguration(final List<String> configuration) {
        if (configuration == null) {
            userConfiguration.setConfiguration(Collections.<String>emptyList());
        } else {
            userConfiguration.setConfiguration(configuration);
        }

        logger.info("set configuration" + userConfiguration);
    }

    // Submit button
    public Resolution pushConfiguration() {
        userConfigurationService.pushConfiguration(userConfiguration);
        
        return new ForwardResolution(VIEW);
    }
}
