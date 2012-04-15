package com.googlecode.sc2dm.annotation.processor;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:17 PM - 11/20/11
 */
public interface ManifestProcessor {
    public boolean isValidManifestDir();
    public void process();
}
