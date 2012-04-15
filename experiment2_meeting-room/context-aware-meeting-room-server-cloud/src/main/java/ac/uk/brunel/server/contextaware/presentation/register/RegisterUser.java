package ac.uk.brunel.server.contextaware.presentation.register;

import ac.uk.brunel.server.contextaware.presentation.BasePage;
import ac.uk.brunel.server.contextaware.presentation.propertymodel.UserWicketPropertyModel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 14, 2010
 * Time: 7:10:55 PM
 */
public class RegisterUser extends BasePage {

    public RegisterUser() {
        final UserWicketPropertyModel wicketUser = new UserWicketPropertyModel();
        final IModel<UserWicketPropertyModel> propModel = new CompoundPropertyModel<UserWicketPropertyModel>(wicketUser);

        final Form<UserWicketPropertyModel> registerForm = new Form<UserWicketPropertyModel>("register_user_form", propModel) {
            @Override
            protected void onSubmit() {
                setResponsePage(new ConfirmRegistration(wicketUser));
            }
        };

        TextField<String> emailTxt = new RequiredTextField<String>("email");
        emailTxt.add(EmailAddressValidator.getInstance());

        registerForm.add(emailTxt);
        registerForm.add(new RequiredTextField<String>("firstName"));
        registerForm.add(new RequiredTextField<String>("lastName"));

        TextField<String> btAdressTxt = new RequiredTextField<String>("btAddress");
        btAdressTxt.add(StringValidator.exactLength(12));

        registerForm.add(btAdressTxt);

        add(registerForm);
        add(new FeedbackPanel("validation_messages"));
    }
}
