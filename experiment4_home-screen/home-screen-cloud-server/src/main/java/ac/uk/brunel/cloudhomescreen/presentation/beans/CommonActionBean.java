package ac.uk.brunel.cloudhomescreen.presentation.beans;

import ac.uk.brunel.cloudhomescreen.presentation.beans.util.CommonViewUtil;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 4:28:57 PM - Nov 20, 2010
 */
public abstract class CommonActionBean implements ActionBean {
    private final Logger logger = LoggerFactory.getLogger(CommonActionBean.class);

    protected ActionBeanContext ctx;
    protected String user;

    @Override
    public void setContext(ActionBeanContext actionBeanContext) {
        ctx = actionBeanContext;

        if (ctx.getRequest() != null && ctx.getRequest().getUserPrincipal() != null) {
            user = ctx.getRequest().getUserPrincipal().getName();
        } else {
            user = "localtestuser@test.no";
            logger.warn("Using test user, this should only be used on local development server!");
        }
    }

    @Override
    public ActionBeanContext getContext() {
        return ctx;
    }

    public String getLogoutURL() {
        return CommonViewUtil.createLogoutURL();
    }

    public String getUser() {
        return user;
    }
}
