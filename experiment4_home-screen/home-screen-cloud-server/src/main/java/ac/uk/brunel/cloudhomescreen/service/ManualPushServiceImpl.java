package ac.uk.brunel.cloudhomescreen.service;

import ac.uk.brunel.cloudhomescreen.config.sources.MessageType;
import ac.uk.brunel.cloudhomescreen.dto.Payload;
import ac.uk.brunel.cloudhomescreen.dto.UserConfiguration;
import ac.uk.brunel.cloudhomescreen.persistence.configuration.UserConfigurationRetrieverDao;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:16 PM - 1/9/11
 */
public class ManualPushServiceImpl implements ManualPushService {
    private final Logger logger = LoggerFactory.getLogger(ManualPushServiceImpl.class);
    private final UserConfigurationRetrieverDao userConfigurationRetrieverDao;

    @Inject
    public ManualPushServiceImpl(final UserConfigurationRetrieverDao userConfigurationRetrieverDao) {
        this.userConfigurationRetrieverDao = userConfigurationRetrieverDao;
    }

    public String getPersistedUserInfo(final String type, final String email) {
        String message = "";

        if (type != null && type.length() > 0 && isNumber(type) && email != null && email.length() > 0) {
            message = buildResponse(type, email);
        } else {
            logger.error("Invalid type received, type: " + type);
        }

        return message;
    }

    private boolean isNumber(final String type) {
        return type.matches("[\\d]+");
    }

    private String buildResponse(final String type, final String email) {
        final StringBuilder responseContent = new StringBuilder();
        final int typeNumber = Integer.parseInt(type);

        if (typeNumber == MessageType.APPLICATION.getType()) {
            UserConfiguration userConfiguration = userConfigurationRetrieverDao.getConfiguration(email);
            responseContent.append(userConfiguration.toUrlString());
        } else if (typeNumber == MessageType.PUSH_MESSAGE.getType()) {
            responseContent.append(new Payload().toUrlString());
            // TODO add configuration
        } else {
            logger.error("Invalid type number received, type: " + typeNumber);
        }

        return responseContent.toString();
    }
}
