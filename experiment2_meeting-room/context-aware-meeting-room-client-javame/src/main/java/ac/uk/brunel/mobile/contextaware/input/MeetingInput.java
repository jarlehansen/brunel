package ac.uk.brunel.mobile.contextaware.input;

public interface MeetingInput {
	public void setNote(final String meetingId, final String message);
	public void setErrorMessage(final String message);
}
