package ac.uk.brunel.mobile.contextaware.participant.presentation;

import it.gerdavax.android.bluetooth.LocalBluetoothDevice;
import ac.uk.brunel.mobile.contextaware.participant.business.PresentationService;
import ac.uk.brunel.mobile.contextaware.participant.callback.PresentationCallback;
import ac.uk.brunel.mobile.contextaware.presenter.config.MeetingRoomModule;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ParticipantClientMain extends Activity implements PresentationCallback {
	private static final String TAG = ParticipantClientMain.class.getName();

	private PresentationService presentationService;
	private LocalBluetoothDevice localDevice = null;
	private String defaultBluetoothAddress = null;
	private String defaultServerAddress = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Injector injector = Guice.createInjector(new MeetingRoomModule());
		presentationService = injector.getInstance(PresentationService.class);

		setContentView(R.layout.main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		createMainButtons();
	}

	@Override
	protected void onDestroy() {
		if (localDevice != null) {
			localDevice.close();
		}
		super.onDestroy();
	}

	private void createMainButtons() {
		Button presentationButton = (Button) findViewById(R.id.presentationButton);
		presentationButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentView(R.layout.presentation);
				createPresentationButtons();

				startReadingNotes();
			}
		});

		Button serverConfigButton = (Button) findViewById(R.id.serverConfigButton);
		serverConfigButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setContentView(R.layout.configuration);
				createConfigurationButtons();

				EditText serverAddressTextField = (EditText) findViewById(R.id.serverAddressInput);
				serverAddressTextField.setText(getSavedServerAddress());
				EditText bluetoothAddressTextField = (EditText) findViewById(R.id.bluetoothAddressInput);
				bluetoothAddressTextField.setText(getSavedBluetoothAddress());
			}
		});

		Button exitButton = (Button) findViewById(R.id.exitButton);
		exitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void createPresentationButtons() {
		Button nextButton = (Button) findViewById(R.id.nextButton);
		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView noteText = (TextView) findViewById(R.id.noteText);

				if (noteText != null) {
					String note = presentationService.getNextNote();

					if (note != null) {
						noteText.setText(note);
					}
				}
			}
		});

		Button prevButton = (Button) findViewById(R.id.prevButton);
		prevButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView noteText = (TextView) findViewById(R.id.noteText);

				if (noteText != null) {
					String note = presentationService.getPreviousNote();

					if (note != null) {
						noteText.setText(note);
					}
				}
			}
		});
	}

	private void createConfigurationButtons() {
		Button saveButton = (Button) findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText serverAddressTextField = (EditText) findViewById(R.id.serverAddressInput);
				EditText bluetoothAddressTextField = (EditText) findViewById(R.id.bluetoothAddressInput);
				saveConfiguration(serverAddressTextField.getText().toString(), bluetoothAddressTextField.getText().toString());

				InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				in.hideSoftInputFromWindow(serverAddressTextField.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				in.hideSoftInputFromWindow(bluetoothAddressTextField.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

				setContentView(R.layout.main);
				createMainButtons();

			}
		});
	}

	private String getSavedServerAddress() {
		if (defaultServerAddress == null || "".equals(defaultServerAddress)) {
			defaultServerAddress = getString(R.string.defaultServerAddress);
		}

		SharedPreferences sharedPreferences = getSharedPreferences(PreferencesConstants.PREF_NETWORK_CONFIG, Activity.MODE_PRIVATE);
		String serverAddress = sharedPreferences.getString(PreferencesConstants.KEY_SERVER_ADDRESS, defaultServerAddress);

		Log.d(TAG, "Server address: " + serverAddress);

		return serverAddress;
	}

	public String getSavedBluetoothAddress() {
		if (defaultBluetoothAddress == null || "".equals(defaultBluetoothAddress)) {
			defaultBluetoothAddress = initBluetoothAddress();

			if (defaultBluetoothAddress == null) {
				defaultBluetoothAddress = getString(R.string.defaultBluetoothAddress);
			}
		}

		SharedPreferences sharedPreferences = getSharedPreferences(PreferencesConstants.PREF_NETWORK_CONFIG, Activity.MODE_PRIVATE);
		String bluetoothAddress = sharedPreferences.getString(PreferencesConstants.KEY_BT_ADDRESS, defaultBluetoothAddress);

		Log.d(TAG, "Bluetooth address: " + bluetoothAddress);

		return bluetoothAddress;
	}

	private String initBluetoothAddress() {
		if (defaultServerAddress == null || "".equals(defaultBluetoothAddress)) {
			defaultBluetoothAddress = initBluetoothAddress();
		}
		String btAddress = null;
		try {
			localDevice = LocalBluetoothDevice.initLocalDevice(getApplicationContext());
			String tempBtAddress = localDevice.getAddress();

			if (tempBtAddress != null && tempBtAddress.contains(":")) {
				btAddress = tempBtAddress.replaceAll(":", "");
			}
		} catch (Exception be) {
			Log.e(TAG, "Exception when trying the get Bluetooth address: " + be.toString());
		}

		return btAddress;
	}

	private void saveConfiguration(final String serverAddress, final String bluetoothAddress) {
		Log.d(TAG, "Saving configuration: " + serverAddress + ", " + bluetoothAddress);

		SharedPreferences sharedPreferences = getSharedPreferences(PreferencesConstants.PREF_NETWORK_CONFIG, MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(PreferencesConstants.KEY_SERVER_ADDRESS, serverAddress);
		editor.putString(PreferencesConstants.KEY_BT_ADDRESS, bluetoothAddress);
		editor.commit();
	}

	private void startReadingNotes() {
		presentationService.startReadingPresentationNotes(this, getSavedServerAddress(), getSavedBluetoothAddress());
	}

	public void setNoteText(final String note) {
		TextView noteText = (TextView) findViewById(R.id.noteText);

		if (noteText != null) {
			presentationService.addNote(note);
			noteText.setText(note);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		final boolean keyDown;

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setContentView(R.layout.main);
			createMainButtons();
			keyDown = false;
		} else {
			keyDown = super.onKeyDown(keyCode, event);
		}

		return keyDown;
	}
}