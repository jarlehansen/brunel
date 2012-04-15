package com.googlecode.sc2dm.ping.config;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.googlecode.sc2dm.ping.servlet.PingServlet;
import com.googlecode.sc2dm.ping.servlet.datastore.UserInfoDao;
import com.googlecode.sc2dm.ping.servlet.datastore.UserInfoDaoImpl;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:20 PM - 10/6/11
 */
public class PingServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        ServletModule servletModule = new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/*").with(PingServlet.class);
            }
        };

        Module module = new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(UserInfoDao.class).to(UserInfoDaoImpl.class);
            }
        };

        return Guice.createInjector(module, servletModule);
    }
}
