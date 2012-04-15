package ac.uk.brunel.server.contextaware.config;

import ac.uk.brunel.server.contextaware.config.integration.testclasses.AopTestClass;
import ac.uk.brunel.server.contextaware.config.integration.testclasses.AopTestClassImpl;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 6:21:02 PM
 */
public class AopTestModule extends MeetingUserModule {
    @Override
    protected void configureServlets() {
        super.configureServlets();
        bind(AopTestClass.class).to(AopTestClassImpl.class);
    }
}
