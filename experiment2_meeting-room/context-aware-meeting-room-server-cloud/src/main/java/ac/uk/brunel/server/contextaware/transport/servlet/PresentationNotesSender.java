package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.contextaware.network.generated.NoteProtobuf;
import ac.uk.brunel.server.contextaware.service.PresentationService;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 4:44:05 PM
 */
@Singleton
public class PresentationNotesSender extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PresentationNotesSender.class);
    private final Provider<PresentationService> presentationServiceProvider;

    @Inject
    public PresentationNotesSender(final Provider<PresentationService> presentationServiceProvider) {
        this.presentationServiceProvider = presentationServiceProvider;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        sendCurrentPresentationNote(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        sendCurrentPresentationNote(request, response);
    }

    public void sendCurrentPresentationNote(final HttpServletRequest request, final HttpServletResponse response) {
        NoteProtobuf.Note inputNote = readFromStream(request);

        if (inputNote != null) {
            final PresentationService presentationService = presentationServiceProvider.get();

            final NoteProtobuf.Note outputNote;
            if (inputNote.hasMeetingId()) {
                outputNote = createNoteWithPreviousMeetingId(presentationService, inputNote);
            } else {
                outputNote = findAndCreateWithNoteMeetingId(presentationService, inputNote);
            }

            sendNoteToStream(outputNote, response);
        }
    }

    private NoteProtobuf.Note readFromStream(final HttpServletRequest request) {
        InputStream input = null;
        NoteProtobuf.Note inputNote = null;

        try {
            input = request.getInputStream();
            inputNote = NoteProtobuf.Note.parseFrom(input);

            if (logger.isDebugEnabled()) {
                logger.debug("readFromStream input: " + inputNote);
            }
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("IOException when trying to read the Note object from the input stream", io);
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException io) {
                }
            }
        }

        return inputNote;
    }

    private NoteProtobuf.Note createNoteWithPreviousMeetingId(final PresentationService presentationService, final NoteProtobuf.Note inputNote) {
        final String meetingId = inputNote.getMeetingId();
        final String btAddress = inputNote.getBtAddress();
        final String message = presentationService.getCurrentSlideNote(meetingId, btAddress);

        final NoteProtobuf.Note.Builder outputNoteBuilder = NoteProtobuf.Note.newBuilder();
        outputNoteBuilder.setBtAddress(btAddress);
        outputNoteBuilder.setMessage(message);

        return outputNoteBuilder.build();
    }

    private NoteProtobuf.Note findAndCreateWithNoteMeetingId(final PresentationService presentationService, final NoteProtobuf.Note inputNote) {
        final String btAddress = inputNote.getBtAddress();
        final String[] noteArray = presentationService.getCurrentSlideNote(btAddress);

        final NoteProtobuf.Note.Builder outputNoteBuilder = NoteProtobuf.Note.newBuilder();
        outputNoteBuilder.setBtAddress(btAddress);

        String meetingId = noteArray[0];
        if (meetingId == null || "".equals(meetingId)) {
            outputNoteBuilder.setMessage("no started meeting found");
        } else {
            outputNoteBuilder.setMeetingId(noteArray[0]);

            String note = noteArray[1];
            if (note == null || "".equals(note)) {
                outputNoteBuilder.setMessage("No current presentation note");
            } else {
                outputNoteBuilder.setMessage(noteArray[1]);
            }
        }

        return outputNoteBuilder.build();
    }

    private void sendNoteToStream(final NoteProtobuf.Note note, final HttpServletResponse response) {
        OutputStream output = null;

        try {
            if (logger.isDebugEnabled()) {
                logger.debug("sendNoteToStream: " + note);
            }

            output = response.getOutputStream();
            note.writeTo(output);
            output.flush();
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("IOException when trying to write the Note object to the stream, " + note, io);
            }
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException io) {
                }
            }
        }
    }
}
