package ac.uk.brunel.mobile.contextaware.servicelocator;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class AppStarterTest {
	private AppConfig mockedAppConfig;
	
	@Before
	public void setup() {
		mockedAppConfig = mock(AppConfig.class);
		AppStarter.load(mockedAppConfig);
	}
	
	@Test
	public void testInitialize() {
		AppStarter.initialize(null);
		
		verify(mockedAppConfig).init(null);
	}
}
