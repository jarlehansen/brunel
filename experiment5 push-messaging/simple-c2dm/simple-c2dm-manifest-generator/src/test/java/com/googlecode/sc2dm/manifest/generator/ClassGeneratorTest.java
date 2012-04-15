package com.googlecode.sc2dm.manifest.generator;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:37 PM - 9/26/11
 */
public class ClassGeneratorTest {

    @Test
    public void generate() {
        String generated = new ClassGenerator().generate("com.test");

        assertTrue(generated.contains("package com.test"));
    }
}
