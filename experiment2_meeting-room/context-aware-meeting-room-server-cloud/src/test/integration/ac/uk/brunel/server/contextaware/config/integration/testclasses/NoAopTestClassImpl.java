package ac.uk.brunel.server.contextaware.config.integration.testclasses;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 6:23:42 PM
 */
public class NoAopTestClassImpl implements AopTestClass {

    
    public String inputAndOutput(final String input) {
        return "test text";
    }

    public String output() {
        return "constant";
    }

    public void input(String input) {
    }

    public void doNothing() {
    }
}
