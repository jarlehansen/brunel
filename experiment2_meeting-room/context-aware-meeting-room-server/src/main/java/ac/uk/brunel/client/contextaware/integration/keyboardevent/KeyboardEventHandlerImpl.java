package ac.uk.brunel.client.contextaware.integration.keyboardevent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 18, 2010
 * Time: 7:25:36 PM
 */
public class KeyboardEventHandlerImpl implements KeyboardEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(KeyboardEventHandlerImpl.class);
    private final Robot robot;

    public KeyboardEventHandlerImpl() {
        try {
            robot = new Robot();
        } catch (AWTException ae) {
            if (logger.isErrorEnabled()) {
                logger.error("Unable to create the Robot object for KeyboardEvents", ae);
            }
            throw new IllegalStateException("Unable to create the Robot object for KeyboardEvents", ae);
        }
    }

    public void performEvent(final String event) {
        final int intEvent = ValidKeyboardEvents.DEFAULT.getIntKeyEvent(event);

        if (intEvent > -1) {
            if (logger.isDebugEnabled()) {
                logger.debug("Performing KeyboardEvent: " + event);
                robot.keyPress(intEvent);
                robot.keyRelease(intEvent);
            } else {
                if (logger.isWarnEnabled()) {
                    logger.warn("The KeyboardEvent " + event + " is not valid");
                }
            }
        }
    }
}
