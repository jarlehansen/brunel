package ac.uk.brunel.server.contextaware.integration.calendar;

import ac.uk.brunel.server.contextaware.exception.calendar.CalendarCommunicationException;
import ac.uk.brunel.server.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 24, 2010
 * Time: 5:03:17 PM
 */
public class CalendarLogonImpl implements CalendarLogon {
    private static final Logger logger = LoggerFactory.getLogger(CalendarLogonImpl.class);
    private static final int MAX_NUM_LOGON_ATTEMPTS = 3;

    CalendarLogonImpl() {
    }

    public void accountLogon(final CalendarService calendarService) {
        String username = PropertiesReader.SERVER_APP.get(PropertiesConstants.SERVER_CALENDAR_USER);
        String password = PropertiesReader.SERVER_APP.get(PropertiesConstants.SERVER_CALENDAR_PASSWORD);

        boolean authenticated = false;
        int counter = 0;

        // Retry connection if an exception was received
        while (!authenticated && counter < MAX_NUM_LOGON_ATTEMPTS) {
            try {
                calendarService.setUserCredentials(username, password);
                authenticated = true;
            } catch (AuthenticationException ae) {
                counter++;

                if (logger.isErrorEnabled()) {
                    final String errorMsg = "Could not authenticate username: " + username;
                    logGoogleServiceError(errorMsg, counter, ae);
                }
            } catch (ServiceException se) {
                counter++;

                if (logger.isErrorEnabled()) {
                    final String errorMsg = "ServiceException while trying to authenticate user: " + username;
                    logGoogleServiceError(errorMsg, counter, se);
                }
            }
        }

        if (!authenticated) {
            throw new CalendarCommunicationException("Could not authenticate user " + username);
        } else {
            if(logger.isInfoEnabled()) {
                logger.info("User " + username + " is authenticated");
            }
        }
    }

     private void logGoogleServiceError(final String errorMsg, final int counter, final Throwable throwable) {
        if (counter < MAX_NUM_LOGON_ATTEMPTS) {
            logger.error(errorMsg + ", retrying", throwable);
        } else {
            logger.error(errorMsg + ", no more retries", throwable);
        }

    }
}
