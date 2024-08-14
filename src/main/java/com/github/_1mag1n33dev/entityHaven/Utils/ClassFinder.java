package com.github._1mag1n33dev.entityHaven.Utils;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class ClassFinder {

    public static <T> Set<Class<? extends T>> findSubclasses(Class<T> baseClass) {
        Set<Class<? extends T>> subclasses = new HashSet<>();

        for (Class<?> clazz : getAllClasses()) {
            if (baseClass.isAssignableFrom(clazz) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                subclasses.add(clazz.asSubclass(baseClass));
            }
        }

        return subclasses;
    }

    private static Set<Class<?>> getAllClasses() {
        return new HashSet<>();
    }
}

