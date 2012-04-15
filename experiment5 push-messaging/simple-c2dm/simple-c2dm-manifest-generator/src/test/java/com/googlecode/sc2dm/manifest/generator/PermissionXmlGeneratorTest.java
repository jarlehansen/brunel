package com.googlecode.sc2dm.manifest.generator;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:39 PM - 9/26/11
 */
public class PermissionXmlGeneratorTest {

    @Test
    public void generate() {
        String generated = new PermissionXmlGenerator().generate("com.test");

        assertTrue(generated.contains("com.test"));
    }
}
