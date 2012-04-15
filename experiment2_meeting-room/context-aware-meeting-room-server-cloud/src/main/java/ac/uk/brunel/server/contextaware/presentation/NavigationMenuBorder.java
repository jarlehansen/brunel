package ac.uk.brunel.server.contextaware.presentation;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 15, 2010
 * Time: 2:59:17 PM
 */
class NavigationMenuBorder extends Border {

    NavigationMenuBorder(final String componentName) {
        super(componentName);
        add(new Label("title", "Context-aware Meeting Room"));
    }
}
