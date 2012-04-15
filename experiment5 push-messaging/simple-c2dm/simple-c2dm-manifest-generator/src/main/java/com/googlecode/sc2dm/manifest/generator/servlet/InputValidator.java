package com.googlecode.sc2dm.manifest.generator.servlet;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:16 PM - 9/25/11
 */
enum InputValidator {
    ;

    static boolean validPackageName(String packageName) {
        boolean isValid = false;

        if (packageName.matches("^([a-z_]{1}[a-z0-9_]*(\\.[a-z_]{1}[a-z0-9_]*)*)$")) {
            isValid = true;
        }

        return isValid;
    }
}
