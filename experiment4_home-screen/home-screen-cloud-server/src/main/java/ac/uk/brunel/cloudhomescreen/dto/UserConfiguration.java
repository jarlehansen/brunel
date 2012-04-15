package ac.uk.brunel.cloudhomescreen.dto;

import ac.uk.brunel.cloudhomescreen.config.sources.ApplicationSources;
import ac.uk.brunel.cloudhomescreen.config.sources.MessageType;
import ac.uk.brunel.cloudhomescreen.config.sources.PushMessageSource;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:07:11 PM - Nov 20, 2010
 */
public class UserConfiguration {
    @Id
    private String user;
    private List<String> configuration;
    private List<String> applications;

    public UserConfiguration() {
    }

    public static UserConfiguration initializeDefaultUserConfiguration(final String user) {
        return new UserConfiguration(user);
    }

    private UserConfiguration(final String user) {
        this.user = user;

        // Default values
        configuration = new ArrayList<String>();
        configuration.add(PushMessageSource.CONTINUOUS_INTEGRATION.getSource());

        applications = new ArrayList<String>();
        applications.add(ApplicationSources.CALENDAR.getSource());
        applications.add(ApplicationSources.EMAIL.getSource());
    }

    public String getUser() {
        return user;
    }

    public void setConfiguration(final List<String> configuration) {
        this.configuration = configuration;
    }

    public void setApplications(final List<String> applications) {
        this.applications = applications;
    }

    public List<String> getConfiguration() {
        return configuration;
    }

    public List<String> getApplications() {
        return applications;
    }

    public String toMessageString() {
        StringBuilder sb = new StringBuilder();
        sb.append("data.type=").append(MessageType.APPLICATION.getType()).append("&");

        if (applications.size() > 0) {
            sb.append("data.apps=");

            for (String app : applications) {
                sb.append(ApplicationSources.getApplicationNumber(app)).append(";");
            }

            sb.append("&");
        }

        if(configuration.size() > 0) {
            sb.append("data.conf=");

            for(String conf : configuration) {
                sb.append(PushMessageSource.getConfigurationNumber(conf)).append(";");
            }
        }

        return sb.toString();
    }

    public String toUrlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type=").append(MessageType.APPLICATION.getType()).append("&");

        if (applications.size() > 0) {
            sb.append("apps=");

            for (String app : applications) {
                sb.append(ApplicationSources.getApplicationNumber(app)).append(";");
            }

            sb.append("&");
        }

        if(configuration.size() > 0) {
            sb.append("conf=");

            for(String conf : configuration) {
                sb.append(PushMessageSource.getConfigurationNumber(conf)).append(";");
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UserConfiguration");
        sb.append("{user='").append(user).append('\'');
        sb.append(", configuration=").append(configuration);
        sb.append(", applications=").append(applications);
        sb.append('}');
        return sb.toString();
    }
}
