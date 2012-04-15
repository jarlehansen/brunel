package com.googlecode.sc2dm.manifest.generator;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:16 PM - 9/24/11
 */
public class ClassGenerator implements Generator {

    public ClassGenerator() {
    }

    public String generate(String packageName) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(";\n\n");
        sb.append("import com.googlecode.sc2dm.integration.SC2DMReceiver;\n\n");
        sb.append("public class C2DMReceiver extends SC2DMReceiver {\n");
        sb.append("}");

        return sb.toString();
    }
}
