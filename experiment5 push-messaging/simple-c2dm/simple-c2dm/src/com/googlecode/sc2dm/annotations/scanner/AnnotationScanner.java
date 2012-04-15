package com.googlecode.sc2dm.annotations.scanner;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:36 PM - 10/2/11
 */
public class AnnotationScanner {
    private Field dexField;
    private final String packageName;

    private final List<Class<?>> classes = new ArrayList<Class<?>>();

    public AnnotationScanner(String packageName) {
        this.packageName = packageName;
        initializeField();
    }

    private void initializeField() {
        try {
            dexField = PathClassLoader.class.getDeclaredField("mDexs");
            dexField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Unable to access 'mDexs'-field on the PathClassLoader");
        }
    }

    public List<Class<?>> scan() {
        try {
            PathClassLoader classLoader = getPathClassLoader();
            DexFile[] dexFiles = (DexFile[]) dexField.get(classLoader);

            getDexEntries(dexFiles, classLoader);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        return classes;
    }

    private PathClassLoader getPathClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        if (cl instanceof PathClassLoader) {
            return (PathClassLoader) cl;
        } else {
            throw new IllegalStateException("Thread.currentThread().getContextClassLoader() did not return object of type PathClassLoader");
        }
    }

    private void getDexEntries(DexFile[] dexFiles, PathClassLoader classLoader) {
        for (DexFile dex : dexFiles) {
            Enumeration<String> entries = dex.entries();
            while (entries.hasMoreElements()) {
                String className = entries.nextElement();

                if (classIsInPackage(className)) {
                    classes.add(dex.loadClass(className, classLoader));
                }
            }
        }
    }

    private boolean classIsInPackage(String className) {
        return (className != null && className.startsWith(packageName));
    }
}
