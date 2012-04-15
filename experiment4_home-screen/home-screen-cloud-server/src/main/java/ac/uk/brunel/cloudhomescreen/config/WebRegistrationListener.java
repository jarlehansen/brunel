package ac.uk.brunel.cloudhomescreen.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 9:35 PM - 1/7/11
 */
public class WebRegistrationListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new WebRegistrationModule());
    }
}
