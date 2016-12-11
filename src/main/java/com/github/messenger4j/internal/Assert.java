package com.github.messenger4j.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * <b>Internal</b> helper class used to support checking of constraints and preconditions.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class Assert {

    private Assert() {
    }

    public static void isTrue(boolean expression, String message) {
        Assert.isTrue(expression, IllegalArgumentException.class, message);
    }

    private static <T extends RuntimeException> void isTrue(boolean expression, Class<T> exceptionClass, String message,
                                                            Object... messageParams) {
        try {
            if (!expression) {
                final String exceptionMessage = String.format(message, messageParams);
                throw exceptionClass.getConstructor(String.class).newInstance(exceptionMessage);
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void notNull(Object object, String parameterName) {
        Assert.notNull(object, parameterName, IllegalArgumentException.class);
    }

    public static <T extends RuntimeException> void notNull(Object object, String parameterName, Class<T> exceptionClass) {
        Assert.isTrue(object != null, exceptionClass, "%s is null", parameterName);
    }

    public static void notNullOrBlank(String string, String parameterName) {
        Assert.isTrue(string != null && string.trim().length() > 0, IllegalArgumentException.class, "%s is null or blank",
                parameterName);
    }

    public static void notNullOrEmpty(Collection<?> collection, String parameterName) {
        Assert.notNullOrEmpty(collection, parameterName, IllegalArgumentException.class);
    }

    public static <T extends RuntimeException> void notNullOrEmpty(Collection<?> collection, String parameterName,
                                                                   Class<T> exceptionClass) {

        Assert.isTrue(collection != null && !collection.isEmpty(), exceptionClass, "%s is null or empty", parameterName);
    }

    public static void lengthNotGreaterThan(String string, int length, String parameterName) {
        Assert.isTrue(string.length() <= length, IllegalArgumentException.class, "%s length is greater than %d characters",
                parameterName, length);
    }

    public static void sizeNotGreaterThan(Collection<?> collection, int size, String parameterName) {
        Assert.sizeNotGreaterThan(collection, size, parameterName, IllegalArgumentException.class);
    }

    public static <T extends RuntimeException> void sizeNotGreaterThan(Collection<?> collection, int size,
                                                                       String parameterName, Class<T> exceptionClass) {

        Assert.isTrue(collection.size() <= size, exceptionClass, "%s size is greater than %d", parameterName, size);
    }

    public static <T extends RuntimeException> void sizeNotLessThan(Collection<?> collection, int size,
                                                                       String parameterName, Class<T> exceptionClass) {

        Assert.isTrue(collection.size() >= size, exceptionClass, "%s size is less than %d", parameterName, size);
    }

    public static void startsWith(String string, String prefix, String parameterName) {
        Assert.isTrue(string.startsWith(prefix), IllegalArgumentException.class, "%s does not start with '%s'",
                parameterName, prefix);
    }
}