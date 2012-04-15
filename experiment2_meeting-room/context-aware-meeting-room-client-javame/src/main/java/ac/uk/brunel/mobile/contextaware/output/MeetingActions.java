package ac.uk.brunel.mobile.contextaware.output;

import java.util.Vector;

public interface MeetingActions {
	public void setServerConfig(final String serverAddress);
	public void startServerCommunication();
	public void stopServerCommunication();
	public String getServerConfig();
	public String getBluetoothAddress();
	public String getNextNote();
	public String getPreviousNote();
	public Vector getPreviousMeetings();
	public void exitApp();
}
