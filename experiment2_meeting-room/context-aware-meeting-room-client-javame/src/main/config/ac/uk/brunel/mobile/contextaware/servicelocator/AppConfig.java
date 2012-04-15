package ac.uk.brunel.mobile.contextaware.servicelocator;

import javax.microedition.midlet.MIDlet;

import ac.uk.brunel.mobile.contextaware.presentation.ContextAwareMeetingRoomUi;

public interface AppConfig {
	public ContextAwareMeetingRoomUi init(final MIDlet midlet);
}
