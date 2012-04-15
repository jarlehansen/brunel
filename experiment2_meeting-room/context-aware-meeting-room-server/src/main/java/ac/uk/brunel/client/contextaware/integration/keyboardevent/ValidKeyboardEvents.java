package ac.uk.brunel.client.contextaware.integration.keyboardevent;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 18, 2010
 * Time: 7:26:00 PM
 */
public enum ValidKeyboardEvents {
    DEFAULT;

    private static final String NEXT_EVENT = "next";
    private static final String PREV_EVENT = "prev";

    private final Map<String, Integer> validEvents = new HashMap<String, Integer>();

    private ValidKeyboardEvents() {
        validEvents.put(NEXT_EVENT, KeyEvent.VK_RIGHT);
        validEvents.put(PREV_EVENT, KeyEvent.VK_LEFT);
    }

    public int getIntKeyEvent(final String event) {
        final Integer intEvent = validEvents.get(event);

        return intEvent == null ? -1 : intEvent;
    }

    public boolean isNext(final String event) {
        return NEXT_EVENT.equals(event);
    }

    public boolean isPrevious(final String event) {
        return PREV_EVENT.equals(event);
    }
}
