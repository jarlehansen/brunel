package ac.uk.brunel.server.contextaware.config.integration.testclasses;

import ac.uk.brunel.server.contextaware.annotation.LoggingAspect;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 6:23:34 PM
 */
@LoggingAspect
public class AopTestClassImpl implements AopTestClass {
    public String inputAndOutput(final String input) {
        return "test text";
    }

    public String output() {
        return "constant";
    }

    public void input(final String input) {
    }

    public void doNothing() {
    }
}
