package ac.uk.brunel.mobile.contextaware.integration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.HttpConnection;

import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;
import ac.uk.brunel.contextaware.network.generated.Note;
import ac.uk.brunel.mobile.contextaware.input.MeetingInput;

public class MeetingNoteReaderImpl implements MeetingNoteReader {
	private static final Logger logger = LoggerFactory.getLogger(MeetingNoteReaderImpl.class);

	private final int serverPollFrequency;
	private final NetworkCommunicationUtil networkCommunicationUtil;
	private MeetingInput controller;
	// Package visibility for testing purposes
	CommunicationThread communicationThread = null;

	public MeetingNoteReaderImpl(final int serverPollFrequency, final NetworkCommunicationUtil networkCommunicationUtil) {
		this.serverPollFrequency = serverPollFrequency;
		this.networkCommunicationUtil = networkCommunicationUtil;
	}

	public void setMeetingInput(final MeetingInput controller) {
		this.controller = controller;
	}

	public void stopCommunication() {
		if (communicationThread != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Stopping network communication");
			}

			communicationThread.stopCommunication();
		}
	}

	public void startServerCommunication(final String bluetoothAddress, final String serverAddress) {
		if (logger.isDebugEnabled()) {
			logger.debug("Starting network communication, server address: " + serverAddress);
		}

		communicationThread = new CommunicationThread(bluetoothAddress, serverAddress, controller, networkCommunicationUtil, serverPollFrequency);
		new Thread(communicationThread).start();
	}

	// Package visibility for testing purposes
	static class CommunicationThread implements Runnable {
		private final String bluetoothAddress;
		private final String serverAddress;
		private final MeetingInput controller;
		private final NetworkCommunicationUtil networkCommunicationUtil;
		private final int serverPollFrequency;

		private String latestMessage = "";
		private String meetingId = "";
		private boolean continueReading = true;
		private int serverTimeoutCounter = 0;
		
		public CommunicationThread(final String bluetoothAddress, final String serverAddress, final MeetingInput controller,
				final NetworkCommunicationUtil networkCommunicationUtil, final int serverPollFrequency) {
			this.bluetoothAddress = bluetoothAddress;
			this.serverAddress = serverAddress;
			this.controller = controller;
			this.networkCommunicationUtil = networkCommunicationUtil;
			this.serverPollFrequency = serverPollFrequency;
		}
		
		public void stopCommunication() {
			continueReading = false;
		}

		public void run() {
			while (continueReading) {
				if (logger.isDebugEnabled()) {
					logger.debug("Polling server: " + serverAddress);
				}

				HttpConnection connection = null;
				OutputStream output = null;
				InputStream input = null;

				try {
					connection = networkCommunicationUtil.createConnector(serverAddress);
					output = connection.openOutputStream();
					writeToStream(output);
					
					input = connection.openInputStream();
					parseFromStream(input);
					
					if(logger.isDebugEnabled()) {
						logger.debug("Response message: " + connection.getResponseMessage());
					}
				} catch (IOException io) {
					if (logger.isErrorEnabled()) {
						logger.error("IOException when parsing the Note Protobuf message", io);
					}
					
					updateTimeoutCounter(io.getMessage());
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (IOException io) {
						}
					}
					if (input != null) {
						try {
							input.close();
						} catch (IOException io) {
						}
					}
					if (connection != null) {
						try {
							connection.close();
						} catch (IOException io) {
						}
					}
				}

				sleepForServerPollFrequency();
			}
		}

		private void writeToStream(final OutputStream output) throws IOException {
			final Note.Builder noteBuilder = Note.newBuilder().setBtAddress(bluetoothAddress);

			if (meetingId != null && !"".equals(meetingId)) {
				noteBuilder.setMeetingId(meetingId);
			}
			
			final Note note = noteBuilder.build();
			if(logger.isDebugEnabled()) {
				logger.debug("Writing note to stream: " + note);
			}
			
			note.writeTo(output);
			output.flush();
		}

		private void parseFromStream(final InputStream input) throws IOException {
			if (input.available() > 0) {
				final Note note = Note.parseFrom(input);
				
				if (meetingId == null || "".equals(meetingId)) {
					meetingId = note.getMeetingId();
				}

				if (latestMessage == null || !latestMessage.equals(note.getMessage())) {
					latestMessage = note.getMessage();
					
					if(logger.isDebugEnabled()) {
						logger.debug("Note received: " + note.toString());
					}
					
					controller.setNote(note.getMeetingId(), note.getMessage());
				}
			}
		}
		
		private void updateTimeoutCounter(final String errorMsg) {
			serverTimeoutCounter++;
			
			if(serverTimeoutCounter == 7) {
				controller.setErrorMessage("Error parsing Note object, " + errorMsg);
				continueReading = false;
			}
		}
		
		private void sleepForServerPollFrequency() {
			try {
				Thread.sleep(serverPollFrequency);
			} catch (InterruptedException ie) {
			}
		}
	}
}
