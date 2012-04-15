package com.googlecode.sc2dm.manifest.generator.config;

import com.google.inject.Injector;
import com.googlecode.sc2dm.manifest.generator.servlet.ManifestGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:42 PM - 9/26/11
 */
public class ManifestGeneratorConfigTest {
    private ManifestGeneratorConfig manifestGeneratorConfig;

    @Before
    public void setUp() {
        manifestGeneratorConfig = new ManifestGeneratorConfig();
    }

    @Test
    public void loadBuildTimestamp() {
        String result = manifestGeneratorConfig.loadBuildTimestamp();

        assertEquals("", result);
    }

    @Test
    public void applicationConfig() {
        Injector injector = manifestGeneratorConfig.getInjector();

        assertThat(injector.getInstance(ManifestGenerator.class), is(ManifestGenerator.class));
    }


}
