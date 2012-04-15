package ac.uk.brunel.server.contextaware.integration.config;

import ac.uk.brunel.server.contextaware.config.AopTestModule;
import ac.uk.brunel.server.contextaware.config.integration.testclasses.AopTestClass;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;


/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 6:26:42 PM
 */
public class GuiceAopConfigIntegrationTest {
    private AopTestClass aopTestClass;

    @Before
    public void setup() {
        final Injector aopInjector = Guice.createInjector(new AopTestModule());
        aopTestClass = aopInjector.getInstance(AopTestClass.class);
    }

    @Test
    public void testInputAndOutputAopLogging() {
        aopTestClass.inputAndOutput("This is the input value");
    }

    @Test
    public void testOutputAopLoggin() {
        aopTestClass.output();
    }

    @Test
    public void testInputAopLogging() {
        aopTestClass.input("This is the input value");
    }

    @Test
    public void testNoInputOrOutputAopLogging() {
        aopTestClass.doNothing();
    }
}