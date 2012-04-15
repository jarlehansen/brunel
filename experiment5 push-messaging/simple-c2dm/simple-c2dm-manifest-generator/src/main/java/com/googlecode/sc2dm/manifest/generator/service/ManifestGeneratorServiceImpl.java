package com.googlecode.sc2dm.manifest.generator.service;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.googlecode.sc2dm.manifest.generator.Generator;
import com.googlecode.sc2dm.manifest.generator.datastore.UsageCounterDao;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 8:08 PM - 9/27/11
 */
public class ManifestGeneratorServiceImpl implements ManifestGeneratorService {
    private final Provider<Generator> xmlPermissionsProvider;
    private final Provider<Generator> xmlReceiverProvider;
    private final Provider<Generator> classProvider;
    private final UsageCounterDao usageCounterDao;

    @Inject
    public ManifestGeneratorServiceImpl(@Named("XmlPermissions") Provider<Generator> xmlPermissionsProvider,
                                        @Named("XmlReceiver") Provider<Generator> xmlReceiverProvider,
                                        @Named("Class") Provider<Generator> classProvider,
                                        UsageCounterDao usageCounterDao) {
        this.classProvider = classProvider;
        this.xmlPermissionsProvider = xmlPermissionsProvider;
        this.xmlReceiverProvider = xmlReceiverProvider;
        this.usageCounterDao = usageCounterDao;
    }


    public GeneratedXml generate(String packageName) {
        String generatedXmlPermissions = xmlPermissionsProvider.get().generate(packageName);
        String generatedXmlReceiver = xmlReceiverProvider.get().generate(packageName);
        String generatedClass = classProvider.get().generate(packageName);

        usageCounterDao.storeStats();

        return new GeneratedXml(generatedXmlPermissions, generatedXmlReceiver, generatedClass);
    }
}
