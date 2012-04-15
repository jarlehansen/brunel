package ac.uk.brunel.benchmark.server.config;

import ac.uk.brunel.benchmark.server.persistence.ResultDao;
import ac.uk.brunel.benchmark.server.persistence.ResultDaoImpl;
import ac.uk.brunel.benchmark.server.sender.MessageSenderFacade;
import ac.uk.brunel.benchmark.server.sender.MessageSenderFacadeImpl;
import ac.uk.brunel.benchmark.server.servlet.DeleteServlet;
import ac.uk.brunel.benchmark.server.servlet.MediumMessageSenderServlet;
import ac.uk.brunel.benchmark.server.servlet.result.ResultServlet;
import ac.uk.brunel.benchmark.server.servlet.result.ResultViewerServlet;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:26 PM - 11/27/11
 */
public class XtifyConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        ServletModule servletModule = new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/medium").with(MediumMessageSenderServlet.class);
                serve("/result").with(ResultServlet.class);
                serve("/resultViewer").with(ResultViewerServlet.class);
                serve("/delete").with(DeleteServlet.class);
            }
        };

        Module module = new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(MessageSenderFacade.class).to(MessageSenderFacadeImpl.class);
                binder.bind(ResultDao.class).to(ResultDaoImpl.class);
            }
        };

        return Guice.createInjector(module, servletModule);
    }
}
