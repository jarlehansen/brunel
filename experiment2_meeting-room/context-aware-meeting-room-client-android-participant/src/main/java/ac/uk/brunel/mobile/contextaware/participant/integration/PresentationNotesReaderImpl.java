package ac.uk.brunel.mobile.contextaware.participant.integration;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import com.google.inject.Inject;

import ac.uk.brunel.contextaware.network.generated.NoteProtobuf.Note;
import ac.uk.brunel.mobile.contextaware.participant.callback.PresentationCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class PresentationNotesReaderImpl implements PresentationNotesReader {
	private static final String BUNDLE_KEY = "note";
	
	private final Executor executor = Executors.newSingleThreadExecutor();
	private final HttpClient httpClient;

	private static final AtomicBoolean continueReading = new AtomicBoolean();
	private Handler handler;

	@Inject
	public PresentationNotesReaderImpl(final HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void startCommunication(final PresentationCallback presentationCallback, final String serverAddress, final String bluetoothAddress) {
		continueReading.set(true);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				presentationCallback.setNoteText(msg.getData().getString(BUNDLE_KEY));
			}
		};
		
		executor.execute(new PresentationNotesReaderThread(handler, serverAddress, bluetoothAddress, httpClient));
	}

	public void stopCommunication() {
		continueReading.set(false);
	}

	private static class PresentationNotesReaderThread implements Runnable {
		private static final String TAG = PresentationNotesReaderThread.class.getName();

		private final Handler handler;
		private final String serverAddress;
		private final String bluetoothAddress;
		private final HttpClient httpClient;

		public PresentationNotesReaderThread(final Handler handler, final String serverAddress, final String bluetoothAddress, final HttpClient httpClient) {
			this.handler = handler;
			this.serverAddress = serverAddress;
			this.bluetoothAddress = bluetoothAddress;
			this.httpClient = httpClient;
		}

		@Override
		public void run() {
			String prevNote = "";
			HttpPost post = new HttpPost(serverAddress);

			while (continueReading.get()) {
				try {
					Note outputNote = Note.newBuilder().setBtAddress(bluetoothAddress).build();
					post.setEntity(new ByteArrayEntity(outputNote.toByteArray()));

					HttpResponse response = httpClient.execute(post);
					HttpEntity responseEntity = response.getEntity();
					InputStream input = responseEntity.getContent();

					Note note = Note.parseFrom(input);
					
					if (!prevNote.equals(note.getMessage())) {
						Log.d(TAG, "Note received: " + note.toString());

						Bundle noteBundle = new Bundle();
						noteBundle.putString(BUNDLE_KEY, note.getMessage());
						Message message = handler.obtainMessage();
						message.setData(noteBundle);

						handler.sendMessage(message);
					}
					
					prevNote = note.getMessage();
				} catch (IOException io) {
					Log.e(TAG, "IOException when receiving note from " + serverAddress, io);

					Bundle noteBundle = new Bundle();
					noteBundle.putString(BUNDLE_KEY, "IOException: " + io.getMessage());
					Message message = handler.obtainMessage();
					handler.sendMessage(message);
					continueReading.set(false);
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
				}
			}
		}
	}
}
