package ac.uk.brunel.server.contextaware.presentation.error;

import ac.uk.brunel.server.contextaware.presentation.BasePage;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 3:40:47 PM
 */
public class ErrorPage extends BasePage {

    public ErrorPage(final Page page, final RuntimeException exception) {
        final String pageInfo;

        if (page == null) {
            pageInfo = "No page info";
        } else {
            pageInfo = page.toString();
        }

        final String errorMsg = exception.getMessage();

        final String causeMsg;
        if(exception.getCause() == null) {
            causeMsg = "";
        } else {
            causeMsg = exception.getCause().getMessage();
        }
        
        final String stackTrace = getStackTrace(exception);

        add(new Label("pageInfo", pageInfo));
        add(new Label("errorMsg", errorMsg));
        add(new Label("causeMsg", causeMsg));
        add(new Label("stackTrace", stackTrace));
    }

    private String getStackTrace(final RuntimeException exception) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);

        printWriter.flush();
        stringWriter.flush();

        return stringWriter.toString();
    }
}
