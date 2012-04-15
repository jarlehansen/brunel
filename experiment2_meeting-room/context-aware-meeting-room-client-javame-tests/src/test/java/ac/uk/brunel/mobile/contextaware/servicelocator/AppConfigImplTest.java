package ac.uk.brunel.mobile.contextaware.servicelocator;

import org.junit.Before;
import org.junit.Test;

public class AppConfigImplTest {
	private AppConfigImpl appConfig;
	
	@Before
	public void setup() {
		appConfig = new AppConfigImpl();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConfigInitNullMidlet() {
		appConfig.init(null);
	}
}
