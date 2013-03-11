package es.jtestme.logger;

import java.util.logging.Level;

import org.slf4j.LoggerFactory;

public final class JTestMeLogger {

    static final boolean LOG4J_ENABLED = isLog4jEnabled();
    static final boolean LOGBACK_ENABLED = isLogbackEnabled();

    private static final String INTERNAL_LOGGER_NAME = "es.jtestme";

    private static boolean loggerEnabled = false;

    private JTestMeLogger() {
        super();
    }

    /**
     * @param enabled
     */
    public static void loggerEnabled(final boolean enabled) {
        loggerEnabled = enabled;
    }

    /**
     * @param msg
     */
    public static void debug(final String msg) {
        if (loggerEnabled) {
            if (LOGBACK_ENABLED) {
                org.slf4j.LoggerFactory.getLogger(INTERNAL_LOGGER_NAME).debug(msg);
            } else if (LOG4J_ENABLED) {
                final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(INTERNAL_LOGGER_NAME);
                logger.debug(msg);
            } else {
                final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(INTERNAL_LOGGER_NAME);
                logger.log(Level.FINE, msg);
            }
        }
    }

    /**
     * @param msg
     */
    public static void info(final String msg) {
        if (loggerEnabled) {
            if (LOGBACK_ENABLED) {
                org.slf4j.LoggerFactory.getLogger(INTERNAL_LOGGER_NAME).info(msg);
            } else if (LOG4J_ENABLED) {
                final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(INTERNAL_LOGGER_NAME);
                logger.info(msg);
            } else {
                final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(INTERNAL_LOGGER_NAME);
                logger.log(Level.INFO, msg);
            }
        }
    }

    /**
     * @param msg
     */
    public static void warn(final String msg) {
        warn(msg, null);
    }

    /**
     * @param msg
     * @param throwable
     */
    public static void warn(final String msg, final Throwable throwable) {
        if (loggerEnabled) {
            if (LOGBACK_ENABLED) {
                org.slf4j.LoggerFactory.getLogger(INTERNAL_LOGGER_NAME).warn(msg, throwable);
            } else if (LOG4J_ENABLED) {
                final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(INTERNAL_LOGGER_NAME);
                logger.warn(msg, throwable);
            } else {
                final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(INTERNAL_LOGGER_NAME);
                logger.log(Level.WARNING, msg, throwable);
            }
        }
    }

    /**
     * @param msg
     */
    public static void error(final String msg) {
        error(msg, null);
    }

    /**
     * @param msg
     * @param throwable
     */
    public static void error(final String msg, final Throwable throwable) {
        if (loggerEnabled) {
            if (LOGBACK_ENABLED) {
                org.slf4j.LoggerFactory.getLogger(INTERNAL_LOGGER_NAME).error(msg, throwable);
            } else if (LOG4J_ENABLED) {
                final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(INTERNAL_LOGGER_NAME);
                logger.error(msg, throwable);
            } else {
                final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(INTERNAL_LOGGER_NAME);
                logger.log(Level.SEVERE, msg, throwable);
            }
        }
    }

    private static boolean isLog4jEnabled() {
        try {
            Class.forName("org.apache.log4j.Logger");
            Class.forName("org.apache.log4j.AppenderSkeleton");
            return true;
        } catch (final Throwable e) {
            return false;
        }
    }

    private static boolean isLogbackEnabled() {
        try {
            Class.forName("ch.qos.logback.classic.Logger");
            return Class.forName("ch.qos.logback.classic.LoggerContext").isAssignableFrom(
                    LoggerFactory.getILoggerFactory().getClass());
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }
}
