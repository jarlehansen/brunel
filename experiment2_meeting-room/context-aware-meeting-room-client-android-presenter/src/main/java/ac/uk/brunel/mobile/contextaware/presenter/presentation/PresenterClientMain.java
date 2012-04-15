package ac.uk.brunel.mobile.contextaware.presenter.presentation;

import ac.uk.brunel.mobile.contextaware.presenter.business.PresenterService;
import ac.uk.brunel.mobile.contextaware.presenter.callback.PresentationCallback;
import ac.uk.brunel.mobile.contextaware.presenter.config.MeetingRoomModule;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class PresenterClientMain extends Activity implements PresentationCallback {
	private final static String TAG = PresenterClientMain.class.getName();

	private final static int SERVER_CONFIG_ID = 1;
	private final static int CONNECT_ID = 2;

	private PresenterService presenterService;
	private String defaultServerAddress = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Injector injector = Guice.createInjector(new MeetingRoomModule());
		presenterService = injector.getInstance(PresenterService.class);

		setContentView(R.layout.main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		createButtons();
	}

	private void createButtons() {
		Button nextSlideButton = (Button) findViewById(R.id.nextSlideButton);
		nextSlideButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Next slide button pushed");
				presenterService.sendNextSlideEvent();
			}
		});

		Button prevSlideButton = (Button) findViewById(R.id.prevSlideButton);
		prevSlideButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Previous slide button pushed");
				presenterService.sendPrevSlideEvent();
			}
		});
	}

	/**
	 * Options menu, used by pressing the "Menu" button on the phone
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, CONNECT_ID, Menu.NONE, "Connect");
		menu.add(Menu.NONE, SERVER_CONFIG_ID, Menu.NONE, "Server config");
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == SERVER_CONFIG_ID) {
			Log.d(TAG, "Server options menu pressed");

			setContentView(R.layout.serverconfig);

			Button saveButton = (Button) findViewById(R.id.saveButton);
			saveButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					EditText serverConfigInput = (EditText) findViewById(R.id.serverConfigInput);
					saveServerAddress(serverConfigInput.getText().toString());
					
					InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					in.hideSoftInputFromWindow(serverConfigInput.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					
					setContentView(R.layout.main);
					createButtons();
				}
			});

			TextView serverConfigInput = (TextView) findViewById(R.id.serverConfigInput);
			serverConfigInput.setText(getSavedServerAddress());
		} else if(item.getItemId() == CONNECT_ID) {
			Log.d(TAG, "Connecting to the meeting room server");
			initConnection();
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void initConnection() {
		String serverAddress = getSavedServerAddress();
		presenterService.initService(serverAddress, this);
		presenterService.sendConnectionPing();
	}
	
	private String getSavedServerAddress() {
		if (defaultServerAddress == null) {
			defaultServerAddress = getString(R.string.defaultServerAddress);
		}

		SharedPreferences sharedPreferences = getSharedPreferences(PreferencesConstants.PREF_NETWORK_CONFIG, Activity.MODE_PRIVATE);
		String serverAddress = sharedPreferences.getString(PreferencesConstants.KEY_SERVER_ADDRESS, defaultServerAddress);

		Log.d(TAG, "Server address: " + serverAddress);

		return serverAddress;
	}

	private void saveServerAddress(final String serverAddress) {
		Log.d(TAG, "Saving server address: " + serverAddress);

		SharedPreferences sharedPreferences = getSharedPreferences(PreferencesConstants.PREF_NETWORK_CONFIG, MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(PreferencesConstants.KEY_SERVER_ADDRESS, serverAddress);
		editor.commit();
	}

	@Override
	public void setStatusMessage(String message) {
		Log.d(TAG, "Status message: " + message);
		TextView statusText = (TextView) findViewById(R.id.statusText);
		statusText.setText(message);
	}
}