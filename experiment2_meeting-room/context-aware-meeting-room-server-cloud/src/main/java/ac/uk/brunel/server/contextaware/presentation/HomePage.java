package ac.uk.brunel.server.contextaware.presentation;

import ac.uk.brunel.server.contextaware.properties.PropertiesConstants;
import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import org.apache.wicket.markup.html.basic.Label;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 16, 2010
 * Time: 8:25:57 PM
 */
public class HomePage extends BasePage {

    public HomePage() {
        add(new Label("message", "Welcome to the Context-Aware Meeting Room Webpage"));
        add(new Label("timestamp", PropertiesReader.BUILD_TIMESTAMP.get(PropertiesConstants.BUILD_TIMESTAMP)));
    }
}
