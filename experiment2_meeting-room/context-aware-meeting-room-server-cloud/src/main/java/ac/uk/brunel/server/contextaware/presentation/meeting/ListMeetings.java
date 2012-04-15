package ac.uk.brunel.server.contextaware.presentation.meeting;

import ac.uk.brunel.server.contextaware.dto.Meeting;
import ac.uk.brunel.server.contextaware.persistence.meeting.MeetingDaoWicketFacade;
import ac.uk.brunel.server.contextaware.presentation.BasePage;
import com.google.inject.Inject;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 25, 2010
 * Time: 9:34:17 PM
 */
public class ListMeetings extends BasePage {
    private final SimpleDateFormat todayQueryFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Inject
    private MeetingDaoWicketFacade meetingDaoWicketFacade;

    public ListMeetings() {
        createPage();
    }

    /**
     * Should only be used for testing!
     */
    ListMeetings(final MeetingDaoWicketFacade meetingDaoWicketFacade) {
        this.meetingDaoWicketFacade = meetingDaoWicketFacade;
        createPage();
    }

    private void createPage() {
        final String queryDateFormat = todayQueryFormat.format(new Date());
        final List<Meeting> meetingList = meetingDaoWicketFacade.getTodaysMeetings(queryDateFormat);
        ListView<Meeting> meetingListView = new ListView<Meeting>("meeting_list", meetingList) {
            @Override
            protected void populateItem(ListItem<Meeting> userListItem) {
                final Meeting meeting = userListItem.getModelObject();

                userListItem.add(new Label("startTime", meeting.getStartTime()));
                userListItem.add(new Label("endTime", meeting.getEndTime()));
                userListItem.add(new Label("title", meeting.getTitle()));
                userListItem.add(new Label("description", meeting.getDescription()));
                userListItem.add(new Label("location", meeting.getLocation()));
                userListItem.add(new Label("presenter", meeting.getPresenter()));
                userListItem.add(new Label("participants", meeting.getParticipants().toString()));

                // Only show presentationLink if it contains a value
                userListItem.add(new ExternalLink("presentationLink", meeting.getPresentationLink()) {
                    @Override
                    public boolean isVisible() {
                        return !"".equals(meeting.getPresentationLink());
                    }
                });
            }
        };
        
        final TextField<String> input = new PasswordTextField("AdminPassword", new Model<String>());
        input.setRequired(true);
        
        final Form<String> deleteMeetingsForms = new Form<String>("deleteMeetingsForm") {
            @Override
            protected void onSubmit() {
                meetingDaoWicketFacade.deleteAllMeetings(input.getInput());
                setResponsePage(ListMeetings.class);
            }
        };
        deleteMeetingsForms.add(input);

        add(new Label("meetingDate", queryDateFormat));
        add(meetingListView);
        add(new ExternalLink("refresh_list_link", "/meetingRefresh"));
        add(deleteMeetingsForms);
        add(new FeedbackPanel("validation_messages"));
    }
}
