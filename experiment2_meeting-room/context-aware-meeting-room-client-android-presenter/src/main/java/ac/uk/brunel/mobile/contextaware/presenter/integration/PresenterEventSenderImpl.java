package ac.uk.brunel.mobile.contextaware.presenter.integration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;

import ac.uk.brunel.contextaware.network.generated.PresenterActionProtobuf.PresenterAction;
import ac.uk.brunel.mobile.contextaware.presenter.callback.PresentationCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.inject.Inject;

public class PresenterEventSenderImpl implements PresenterEventSender {
	private static final String TAG = PresenterEventSenderImpl.class.getName();
	private static final String BUNDLE_KEY = "statusMessage";
	
	private final HttpClient httpClient;
	private final Executor executor = Executors.newSingleThreadExecutor();
	
	private PresentationCallback presentationCallback;
	private Handler threadHandler;
	private static String serverAddress = null;
	
	@Inject
	public PresenterEventSenderImpl(final HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	public void setPresentationCallback(final PresentationCallback presentationCallback) {
		this.presentationCallback = presentationCallback;
	}
	
	public void setServerAddress(final String serverAddress) {
		PresenterEventSenderImpl.serverAddress = serverAddress + PresenterEventConstants.PRESENTER_ACTION_SERVICE;
	}
	
	public void sendNextSlideEvent() {
		sendPresentationEvent(PresenterEventConstants.NEXT_SLIDE);
	}
	
	public void sendPrevSlideEvent() {
		sendPresentationEvent(PresenterEventConstants.PREV_SLIDE);
	}

	private void sendPresentationEvent(final String event) {
		Log.d(TAG, "Sending presentationEvent: " + event);
		
		threadHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				presentationCallback.setStatusMessage(msg.getData().getString(BUNDLE_KEY));
			}
		};
		
		final PresenterAction presenterAction = PresenterAction.newBuilder().setAction(event).build();
		
		executor.execute(new PresenterEventSenderThread(presenterAction, httpClient, threadHandler));
	}

	private static class PresenterEventSenderThread implements Runnable {
		private static final String TAG = PresenterEventSenderThread.class.getName();
		private final PresenterAction presenterAction;
		private final HttpClient httpClient;
		private final Handler handler;
		
		private PresenterEventSenderThread(final PresenterAction presenterAction, final HttpClient httpClient, final Handler handler) {
			this.presenterAction = presenterAction;
			this.httpClient = httpClient;
			this.handler = handler;
		}

		@Override
		public void run() {
			try {
				HttpPost post = new HttpPost(serverAddress);

				BasicHttpEntity entity = new BasicHttpEntity();
				entity.setContent(new ByteArrayInputStream(presenterAction.toByteArray()));
				post.setEntity(entity);
				
				httpClient.execute(post);
			} catch (IOException io) {
				String errorMsg = "Unable to send the PresenterAction object, " + io.getMessage();
				// adding stack trace causes problems with the log
				Log.e(TAG, errorMsg);
				
				Bundle bundle = new Bundle();
				bundle.putString(BUNDLE_KEY, errorMsg);
				Message message = handler.obtainMessage();
				message.setData(bundle);
				
				handler.sendMessage(message);
			}
		}
	}
}
