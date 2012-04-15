package com.googlecode.sc2dm.annotations.scanner;

import junit.framework.TestCase;

import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:56 PM - 10/2/11
 */
public class AnnotationScannerTest extends TestCase {

    public void testScan_noPackage() {
        AnnotationScanner scanner = new AnnotationScanner("");

        List<Class<?>> classes = scanner.scan();

        assertTrue(classes.size() > 0);
        assertTrue(classes.contains(this.getClass()));
    }

    public void testScan_validPackageName() {
        AnnotationScanner scanner = new AnnotationScanner(this.getClass().getPackage().getName());

        List<Class<?>> classes = scanner.scan();

        assertTrue(classes.size() > 0);
        assertTrue(classes.contains(this.getClass()));
    }

    public void testScan_emptyPackage() {
        AnnotationScanner scanner = new AnnotationScanner("not.valid.package");

        List<Class<?>> classes = scanner.scan();

        assertTrue(classes.size() == 0);
    }
}
