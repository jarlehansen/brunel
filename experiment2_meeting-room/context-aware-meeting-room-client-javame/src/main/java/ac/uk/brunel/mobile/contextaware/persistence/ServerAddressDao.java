package ac.uk.brunel.mobile.contextaware.persistence;

public interface ServerAddressDao {
	public void saveServerAddress(final String serverAddress);
	public String getServerAddress();
	public void closeRecordStore();
}
