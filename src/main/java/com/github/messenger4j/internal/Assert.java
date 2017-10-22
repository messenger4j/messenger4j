package com.github.messenger4j.internal;

import java.lang.reflect.InvocationTargetException;

/**
 * <b>Internal</b> helper class for checking constraints and preconditions.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class Assert {

    private Assert() {
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
}