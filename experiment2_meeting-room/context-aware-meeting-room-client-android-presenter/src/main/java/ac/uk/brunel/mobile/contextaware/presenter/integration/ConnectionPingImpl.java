package ac.uk.brunel.mobile.contextaware.presenter.integration;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import ac.uk.brunel.mobile.contextaware.presenter.callback.PresentationCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.inject.Inject;

public class ConnectionPingImpl implements ConnectionPing {
	private static final String TAG = ConnectionPingImpl.class.getName();
	private static final String BUNDLE_KEY = "statusMessage";
	
	private final HttpClient httpClient;
	
	private Handler threadHandler;
	private static String serverAddress = null;
	private PresentationCallback presentationCallback;
	
	@Inject
	public ConnectionPingImpl(final HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	public void setPresentationCallback(final PresentationCallback presentationCallback) {
		this.presentationCallback = presentationCallback;
	}
	
	public void setServerAddress(final String serverAddress) {
		ConnectionPingImpl.serverAddress = serverAddress + PresenterEventConstants.CONNECTION_PING_SERVICE;
	}
	
	public void sendConnectionPing() {
		Log.d(TAG, "Try to send a connection request to: " + serverAddress);
		
		threadHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				presentationCallback.setStatusMessage(msg.getData().getString(BUNDLE_KEY));
			}
		};
		
		threadHandler.post(new ConnectionPingThread(httpClient, threadHandler));
	}
	
	private static class ConnectionPingThread implements Runnable {
		private final HttpClient httpClient;
		private final Handler handler;
		
		public ConnectionPingThread(final HttpClient httpClient, final Handler handler) {
			this.httpClient = httpClient;
			this.handler = handler;
		}
		
		@Override
		public void run() {
			try {
				HttpGet get = new HttpGet(serverAddress);
				HttpResponse response = httpClient.execute(get);
				
				int statusCode = response.getStatusLine().getStatusCode();
				
				Message message = handler.obtainMessage();
				Bundle bundle = new Bundle();
				if(statusCode == HttpStatus.SC_OK) {
					bundle.putString(BUNDLE_KEY, "Connected to meeting room server!");
				} else {
					bundle.putString(BUNDLE_KEY, "Error connecting to server: " + statusCode);
				}
				
				message.setData(bundle);
				handler.sendMessage(message);
			} catch (IOException io) {
				String errorMsg = "Unable to connect to the meeting room server, " + io.getMessage();
				// adding stack trace causes problems with the log
				Log.e(TAG, errorMsg);
				Log.d(TAG, "SERVER ADDRESS: " + serverAddress);
				
				Message message = handler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putString(BUNDLE_KEY, errorMsg);
				message.setData(bundle);
				handler.sendMessage(message);
			}
		}
	}
}
