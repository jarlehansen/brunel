package ac.uk.brunel.server.contextaware.config;

import ac.uk.brunel.server.contextaware.presentation.error.ErrorPage;
import org.apache.wicket.Page;
import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 28, 2010
 * Time: 3:54:17 PM
 */
class WicketRuntimeExceptionHandler extends WebRequestCycle {

    public WicketRuntimeExceptionHandler(WebApplication application, WebRequest request, Response response) {
        super(application, request, response);
    }

    @Override
    public Page onRuntimeException(Page page, RuntimeException exception) {
        return new ErrorPage(page, exception);
    }
}
