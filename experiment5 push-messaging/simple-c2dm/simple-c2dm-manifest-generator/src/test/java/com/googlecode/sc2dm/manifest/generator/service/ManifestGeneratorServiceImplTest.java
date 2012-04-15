package com.googlecode.sc2dm.manifest.generator.service;

import com.google.inject.Provider;
import com.googlecode.sc2dm.manifest.generator.Generator;
import com.googlecode.sc2dm.manifest.generator.datastore.UsageCounterDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:40 PM - 9/27/11
 */
@RunWith(MockitoJUnitRunner.class)
public class ManifestGeneratorServiceImplTest {
    @Mock
    private Provider<Generator> provider;
    @Mock
    private Generator generator;
    @Mock
    private UsageCounterDao usageCounterDao;

    @Test
    public void generate() {
        when(provider.get()).thenReturn(generator);

        ManifestGeneratorServiceImpl service = new ManifestGeneratorServiceImpl(provider, provider, provider, usageCounterDao);
        service.generate("com.test");

        verify(provider, times(3)).get();
        verify(usageCounterDao).storeStats();
    }
}
