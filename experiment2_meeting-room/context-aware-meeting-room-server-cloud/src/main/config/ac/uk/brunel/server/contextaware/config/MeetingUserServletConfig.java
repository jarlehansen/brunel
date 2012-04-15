package ac.uk.brunel.server.contextaware.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 14, 2010
 * Time: 7:37:11 PM
 */
public class MeetingUserServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new MeetingUserModule());
    }
}
