package ac.uk.brunel.server.contextaware.presentation;

import org.apache.wicket.markup.html.basic.Label;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 18, 2010
 * Time: 10:12:48 PM
 */
public class DummyWebPage extends BasePage {
// Only used to test the BasePage & NavigationMenuBorder component

    public DummyWebPage() {
        add(new Label("test", "test"));
    }
}
