package ac.uk.brunel.mobile.contextaware.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

import org.junit.Before;
import org.junit.Test;

public class ServerAddressDaoImplTest {
	private static final String defaultServerAddress = "defaultServerAddress";
	
	private RecordStore mockedRecordStore;
	
	private ServerAddressDaoImpl serverAddressDao;
	
	@Before
	public void setup() throws RecordStoreException {
		mockedRecordStore = mock(RecordStore.class);
		when(mockedRecordStore.getNumRecords()).thenReturn(0);
		when(mockedRecordStore.addRecord(any(byte[].class), anyInt(), anyInt())).thenReturn(1);
		
		serverAddressDao = new ServerAddressDaoImpl(defaultServerAddress, mockedRecordStore);
	}
	
	@Test
	public void testInitRecordStoreNoRecords() throws RecordStoreException {
		serverAddressDao.initRecordStore(defaultServerAddress);
		
		// called 2 times due to constructor call
		verify(mockedRecordStore, times(2)).getNumRecords();
		verify(mockedRecordStore, times(2)).addRecord(defaultServerAddress.getBytes(), 0, defaultServerAddress.getBytes().length);
	}
	
	@Test
	public void testInitRecordStoreExistingRecord() throws RecordStoreException {
		RecordEnumeration mockedRecordEnumeration = mock(RecordEnumeration.class);
		when(mockedRecordEnumeration.hasNextElement()).thenReturn(true);
		when(mockedRecordEnumeration.nextRecordId()).thenReturn(1);
		
		when(mockedRecordStore.enumerateRecords(null, null, false)).thenReturn(mockedRecordEnumeration);
		when(mockedRecordStore.getNumRecords()).thenReturn(1);
		
		serverAddressDao.initRecordStore(defaultServerAddress);
		
		// called 2 times due to constructor call
		verify(mockedRecordStore, times(2)).getNumRecords();
		verify(mockedRecordStore).enumerateRecords(null, null, false);
		verify(mockedRecordEnumeration).hasNextElement();
		verify(mockedRecordEnumeration).nextRecordId();
	}
	
	@Test
	public void testSaveServerAddress() throws RecordStoreException {
		serverAddressDao.saveServerAddress(defaultServerAddress);
		
		verify(mockedRecordStore).deleteRecord(1);
		// called 2 times due to constructor call
		verify(mockedRecordStore, times(2)).addRecord(defaultServerAddress.getBytes(), 0, defaultServerAddress.getBytes().length);
	}
	
	@Test
	public void testSaveServerAddressNoRecords() throws RecordStoreException {
		RecordEnumeration mockedRecordEnumeration = mock(RecordEnumeration.class);
		when(mockedRecordEnumeration.hasNextElement()).thenReturn(true);
		when(mockedRecordEnumeration.nextRecordId()).thenReturn(-1);
		
		when(mockedRecordStore.enumerateRecords(null, null, false)).thenReturn(mockedRecordEnumeration);
		when(mockedRecordStore.getNumRecords()).thenReturn(1);
		
		serverAddressDao.saveServerAddress(defaultServerAddress);
		
		// this should only happen once from the constructor call and not the actual saveServerAddress method since the recordId is -1
		verify(mockedRecordStore, times(1)).deleteRecord(anyInt());
		// called 2 times due to constructor call
		verify(mockedRecordStore, times(2)).addRecord(defaultServerAddress.getBytes(), 0, defaultServerAddress.getBytes().length);
	}
	
	@Test
	public void testGetServerAddress() throws RecordStoreException {
		RecordEnumeration mockedRecordEnumeration = mock(RecordEnumeration.class);
		when(mockedRecordEnumeration.hasNextElement()).thenReturn(true);
		when(mockedRecordEnumeration.nextRecord()).thenReturn(defaultServerAddress.getBytes());
		
		when(mockedRecordStore.enumerateRecords(null, null, false)).thenReturn(mockedRecordEnumeration);
		
		String returnedServerAddress = serverAddressDao.getServerAddress();
		
		verify(mockedRecordStore).enumerateRecords(null, null, false);
		verify(mockedRecordEnumeration).hasNextElement();
		verify(mockedRecordEnumeration).nextRecord();
		assertEquals(defaultServerAddress, returnedServerAddress);
	}
}
