package com.googlecode.sc2dm.manifest.generator.config;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.googlecode.sc2dm.manifest.generator.ClassGenerator;
import com.googlecode.sc2dm.manifest.generator.Generator;
import com.googlecode.sc2dm.manifest.generator.PermissionXmlGenerator;
import com.googlecode.sc2dm.manifest.generator.ReceiverXmlGenerator;
import com.googlecode.sc2dm.manifest.generator.datastore.UsageCounterDao;
import com.googlecode.sc2dm.manifest.generator.datastore.UsageCounterDaoImpl;
import com.googlecode.sc2dm.manifest.generator.service.ManifestGeneratorService;
import com.googlecode.sc2dm.manifest.generator.service.ManifestGeneratorServiceImpl;
import com.googlecode.sc2dm.manifest.generator.servlet.ManifestGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;


/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 4:21 PM - 9/24/11
 */
public class ManifestGeneratorConfig extends GuiceServletContextListener {
    private static final Logger logger = Logger.getLogger(ManifestGeneratorConfig.class.getName());

    @Override
    protected Injector getInjector() {
        ServletModule servletModule = new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/*").with(ManifestGenerator.class);
            }
        };

        final String timestamp = loadBuildTimestamp();

        Module module = new Module() {
            public void configure(Binder binder) {
                binder.bind(ManifestGeneratorService.class).to(ManifestGeneratorServiceImpl.class);
                binder.bind(Generator.class).annotatedWith(Names.named("Class")).to(ClassGenerator.class);
                binder.bind(Generator.class).annotatedWith(Names.named("XmlPermissions")).to(PermissionXmlGenerator.class);
                binder.bind(Generator.class).annotatedWith(Names.named("XmlReceiver")).to(ReceiverXmlGenerator.class);
                binder.bind(UsageCounterDao.class).to(UsageCounterDaoImpl.class);
                binder.bind(String.class).annotatedWith(Names.named("BuildTimestamp")).toInstance(timestamp);
            }
        };

        return Guice.createInjector(module, servletModule);
    }

    String loadBuildTimestamp() {
        String timestamp = "";

        Properties props = new Properties();
        FileInputStream input = null;

        try {
            input = new FileInputStream("build.properties");
            props.load(input);
            timestamp = props.getProperty("timestamp");
        } catch (IOException e) {
            // No worries its just a build timestamp
            logger.warning("Unable to load the build.properties file");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }

        return (timestamp == null) ? "" : timestamp;
    }
}
