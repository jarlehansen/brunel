package ac.uk.brunel.server.contextaware.integration.config;

import ac.uk.brunel.server.contextaware.config.NoAopTestModule;
import ac.uk.brunel.server.contextaware.config.integration.testclasses.AopTestClass;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 7:10:30 PM
 */
public class GuiceNoAopConfigIntegrationTest {
    private AopTestClass noAopTestClass;

    @Before
    public void setup() {
        final Injector noAopInjector = Guice.createInjector(new NoAopTestModule());
        noAopTestClass = noAopInjector.getInstance(AopTestClass.class);
    }

    @Test
    public void testInputAndOutputNoAop() {
        noAopTestClass.inputAndOutput("This is the input value");
    }

    @Test
    public void testOutputNoAop() {
        noAopTestClass.output();
    }

    @Test
    public void testInputNoAop() {
        noAopTestClass.input("This is the input value");
    }

    @Test
    public void testNoInputOrOutputNoAop() {
        noAopTestClass.doNothing();
    }
}
