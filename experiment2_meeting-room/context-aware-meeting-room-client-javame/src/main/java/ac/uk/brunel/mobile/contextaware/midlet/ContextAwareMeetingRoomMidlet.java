package ac.uk.brunel.mobile.contextaware.midlet;

import javax.microedition.midlet.MIDlet;

import ac.uk.brunel.mobile.contextaware.servicelocator.AppStarter;

import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;
import net.sf.microlog.core.PropertyConfigurator;

public class ContextAwareMeetingRoomMidlet extends MIDlet {
	private static final Logger logger = LoggerFactory.getLogger(ContextAwareMeetingRoomMidlet.class);

	public ContextAwareMeetingRoomMidlet() {
		PropertyConfigurator.configure();
	}

	protected void destroyApp(boolean arg0) {
		LoggerFactory.shutdown();
	}

	protected void pauseApp() {
	}

	protected void startApp() {
		if(logger.isInfoEnabled()) {
			logger.info("Starting ContextAwareMeetingRoom");
		}
		
		AppStarter.initialize(this);
	}
}
