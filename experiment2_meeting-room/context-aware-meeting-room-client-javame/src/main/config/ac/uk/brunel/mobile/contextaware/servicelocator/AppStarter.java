package ac.uk.brunel.mobile.contextaware.servicelocator;

import javax.microedition.midlet.MIDlet;

import ac.uk.brunel.mobile.contextaware.presentation.ContextAwareMeetingRoomUi;

public class AppStarter {
	private static AppConfig appConfig = new AppConfigImpl();

	public static void load(final AppConfig appConfig) {
		AppStarter.appConfig = appConfig;
	}

	public static ContextAwareMeetingRoomUi initialize(final MIDlet midlet) {
		return appConfig.init(midlet);
	}
}
