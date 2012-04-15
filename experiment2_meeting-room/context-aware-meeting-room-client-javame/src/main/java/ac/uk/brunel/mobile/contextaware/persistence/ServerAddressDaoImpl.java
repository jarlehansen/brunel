package ac.uk.brunel.mobile.contextaware.persistence;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

public class ServerAddressDaoImpl implements ServerAddressDao {
	private static final Logger logger = LoggerFactory.getLogger(ServerAddressDaoImpl.class);
	private final RecordStore recordStore;
	
	private int currentId = -1;
	
	public ServerAddressDaoImpl(final String defaultServerAddress, final RecordStore recordStore) {
		this.recordStore = recordStore;
		initRecordStore(defaultServerAddress);		
	}
	
	void initRecordStore(final String defaultServerAddress) {
		try {
			if (recordStore.getNumRecords() == 0) {
				byte[] data = defaultServerAddress.getBytes();
				currentId = recordStore.addRecord(data, 0, data.length);
			} else {
				currentId = getCurrentRecordId();
			}
		} catch (RecordStoreException rse) {
			if (logger.isErrorEnabled()) {
				logger.error("Unable to save the defaultServerAddress" + defaultServerAddress + " to the ServerAddress record store", rse);
			}

			throw new IllegalStateException("Unable to save the defaultServerAddress" + defaultServerAddress + " to the ServerAddress record store" + ", " + rse.getMessage());
		}
	}
	
	private int getCurrentRecordId() {
		int savedId = -1;
		
		try {
			RecordEnumeration recordEnumeration = recordStore.enumerateRecords(null, null, false);
			
			if(recordEnumeration.hasNextElement()) {
				savedId = recordEnumeration.nextRecordId();
			}
		} catch(RecordStoreException rse) {
			if(logger.isErrorEnabled()) {
				logger.error("Unable to retrieve the saved record id", rse);
			}
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("Using the previously saved record id: " + savedId);
		}
		
		return savedId;
	}
	
	public void saveServerAddress(final String serverAddress) {
		try {
			if(currentId > -1) {
				recordStore.deleteRecord(currentId);
			}
			
			byte[] data = serverAddress.getBytes();
			currentId = recordStore.addRecord(data, 0, data.length);
			
			if(logger.isDebugEnabled()) {
				logger.debug("Save the server address: " + serverAddress + " to rms with id: " + currentId);
			}
		} catch (RecordStoreException rse) {
			if(logger.isErrorEnabled()) {
				logger.error("RecordStoreException when trying to store the server address, " + serverAddress, rse);
			}
		}
	}

	public String getServerAddress() {
		String serverAddress = "";

		try {
			RecordEnumeration recordEnumeration = recordStore.enumerateRecords(null, null, false);

			if (recordEnumeration.hasNextElement()) {
				byte[] data = recordEnumeration.nextRecord();

				if (data != null && data.length > 0) {
					serverAddress = new String(data);
				}
			}
			
			if(logger.isDebugEnabled()) {
				logger.debug("Returning server address: " + serverAddress);
			}
		} catch (RecordStoreException rse) {
			if(logger.isErrorEnabled()) {
				logger.error("RecordStoreException when trying to retrieve the server address record", rse);
			}
		}
		
		
		return serverAddress;
	}

	public void closeRecordStore() {
		try {
			recordStore.closeRecordStore();
		} catch (RecordStoreNotOpenException rsnoe) {
			if(logger.isWarnEnabled()) {
				logger.warn("RecordStoreNotOpenException when trying to close the ServerAddress record store", rsnoe);
			}
		} catch (RecordStoreException rse) {
			if(logger.isWarnEnabled()) {
				logger.warn("RecordStoreException when trying to close the ServerAddress record store", rse);
			}
		}
	}
}
