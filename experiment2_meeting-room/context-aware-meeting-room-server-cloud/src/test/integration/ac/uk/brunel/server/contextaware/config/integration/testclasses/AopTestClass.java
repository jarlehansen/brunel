package ac.uk.brunel.server.contextaware.config.integration.testclasses;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 6:23:19 PM
 */
public interface AopTestClass {
    public String inputAndOutput(String input);
    public String output();
    public void input(final String input);
    public void doNothing();
}
