package com.googlecode.sc2dm.manifest.generator.servlet;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:26 PM - 9/25/11
 */
public class InputValidatorTest {

    @Test
    public void invalidInput_minus() {
        boolean isValid = InputValidator.validPackageName("test-test");

        assertFalse(isValid);
    }

    @Test
    public void invalidInput_lesserThanGreaterThan() {
        boolean isValid = InputValidator.validPackageName("<test>");

        assertFalse(isValid);
    }

    @Test
    public void invalidInput_andSign() {
        boolean isValid = InputValidator.validPackageName("test&asd");

        assertFalse(isValid);
    }

    @Test
    public void validInput() {
        boolean isValid = InputValidator.validPackageName(this.getClass().getPackage().getName());

        assertTrue(isValid);
    }
}
