package ac.uk.brunel.server.contextaware.presentation.notes;

import ac.uk.brunel.server.contextaware.persistence.presentation.PresentationDaoWicketFacade;
import ac.uk.brunel.server.contextaware.presentation.BasePage;
import com.google.inject.Inject;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;


/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 28, 2010
 * Time: 2:12:04 PM
 */
public class DeleteMeetingNotes extends BasePage {
    @Inject
    private PresentationDaoWicketFacade presentationDaoWicketFacade;

    public DeleteMeetingNotes() {
        createPage();
    }

    DeleteMeetingNotes(final PresentationDaoWicketFacade presentationDaoWicketFacade) {
        this.presentationDaoWicketFacade = presentationDaoWicketFacade;
        createPage();
    }

    private void createPage() {
        final int numberOfMeetingNotes = presentationDaoWicketFacade.getNumberOfMeetingNotes();

        final TextField<String> input = new PasswordTextField("AdminPassword", new Model<String>());
        input.setRequired(true);

        final Form<String> deleteMeetingNotesForm = new Form<String>("deleteMeetingNotesForm") {
            @Override
            protected void onSubmit() {
                presentationDaoWicketFacade.deleteAllMeetingNotes(input.getInput());
                setResponsePage(DeleteMeetingNotes.class);
            }
        };
        deleteMeetingNotesForm.add(input);

        add(new Label("numberOfMeetingNotes", new Integer(numberOfMeetingNotes).toString()));
        add(deleteMeetingNotesForm);
        add(new FeedbackPanel("validation_messages"));
    }
}
