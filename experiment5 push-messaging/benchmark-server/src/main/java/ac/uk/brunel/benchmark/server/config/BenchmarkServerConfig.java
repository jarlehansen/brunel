package ac.uk.brunel.benchmark.server.config;

import ac.uk.brunel.benchmark.server.persistence.*;
import ac.uk.brunel.benchmark.server.sender.MessageSenderFacade;
import ac.uk.brunel.benchmark.server.sender.MessageSenderFacadeImpl;
import ac.uk.brunel.benchmark.server.servlet.DeleteServlet;
import ac.uk.brunel.benchmark.server.servlet.LargeMessageSenderServlet;
import ac.uk.brunel.benchmark.server.servlet.MediumMessageSenderServlet;
import ac.uk.brunel.benchmark.server.servlet.SmallMessageSenderServlet;
import ac.uk.brunel.benchmark.server.servlet.result.BatteryLevelServlet;
import ac.uk.brunel.benchmark.server.servlet.result.BatteryLevelViewerServlet;
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
 * Created: 6:17 PM - 11/21/11
 */
public class BenchmarkServerConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        ServletModule servletModule = new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/small").with(SmallMessageSenderServlet.class);
                serve("/medium").with(MediumMessageSenderServlet.class);
                serve("/large").with(LargeMessageSenderServlet.class);

                serve("/result").with(ResultServlet.class);
                serve("/resultViewer").with(ResultViewerServlet.class);
                serve("/battery").with(BatteryLevelServlet.class);
                serve("/batteryViewer").with(BatteryLevelViewerServlet.class);

                serve("/delete").with(DeleteServlet.class);
            }
        };

        Module module = new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(ResultDao.class).to(ResultDaoImpl.class);
                binder.bind(BatteryLevelDao.class).to(BatteryLevelDaoImpl.class);
                binder.bind(GoogleAuthDao.class).to(GoogleAuthDaoImpl.class);
                binder.bind(MessageSenderFacade.class).to(MessageSenderFacadeImpl.class);
            }
        };

        return Guice.createInjector(module, servletModule);
    }
}
