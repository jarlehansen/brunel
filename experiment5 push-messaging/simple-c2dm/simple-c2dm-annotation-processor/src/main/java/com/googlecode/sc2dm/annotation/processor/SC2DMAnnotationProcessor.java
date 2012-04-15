package com.googlecode.sc2dm.annotation.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:13 PM - 10/4/11
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("com.googlecode.sc2dm.annotations.SC2DMAutomaticManifest")
@SupportedOptions("manifestDir")
public class SC2DMAnnotationProcessor extends AbstractProcessor {
    private static final Logger logger = LoggerFactory.getLogger(SC2DMAnnotationProcessor.class);
    private ManifestProcessor manifestProcessor = null;
    private boolean manifestProcessed = false;

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        String manifestdir = processingEnvironment.getOptions().get("manifestDir");
        logger.info("Manifest directory: {}", manifestdir);

        if (manifestdir != null && !"".equals(manifestdir)) {
            manifestProcessor = new ManifestProcessorImpl(manifestdir);
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> typeElements, RoundEnvironment roundEnvironment) {
        if (!manifestProcessed && manifestProcessor != null && manifestProcessor.isValidManifestDir()) {
            manifestProcessed = true;
            
            logger.info("Processing AndroidManifest.xml");
            manifestProcessor.process();
        }

        return true;
    }
}
