package es.jtestme.logger;

import java.util.logging.Level;

import org.slf4j.LoggerFactory;

public final class JTestMeLogger {

    private static final boolean LOG4J_ENABLED = isLog4jEnabled();
    private static final boolean LOGBACK_ENABLED = isLogbackEnabled();

    private static boolean loggerEnabled = true;

    private JTestMeLogger() {
        super();
    }

    /**
     * @return
     */
    public static boolean isLoggerEnabled() {
        return loggerEnabled;
    }

    /**
     * @param enabled
     */
    public static void setLoggerEnabled(final boolean enabled) {
        loggerEnabled = enabled;
    }

    /**
     * @param msg
     */
    public static void debug(final String msg) {
        if (loggerEnabled) {
            if (LOGBACK_ENABLED) {
                org.slf4j.LoggerFactory.getLogger(getLoggerName()).debug(msg);
            } else if (LOG4J_ENABLED) {
                final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getLoggerName());
                logger.debug(msg);
            } else {
                final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getLoggerName());
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
                org.slf4j.LoggerFactory.getLogger(getLoggerName()).info(msg);
            } else if (LOG4J_ENABLED) {
                final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getLoggerName());
                logger.info(msg);
            } else {
                final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getLoggerName());
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
                org.slf4j.LoggerFactory.getLogger(getLoggerName()).warn(msg, throwable);
            } else if (LOG4J_ENABLED) {
                final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getLoggerName());
                logger.warn(msg, throwable);
            } else {
                final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getLoggerName());
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
                org.slf4j.LoggerFactory.getLogger(getLoggerName()).error(msg, throwable);
            } else if (LOG4J_ENABLED) {
                final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getLoggerName());
                logger.error(msg, throwable);
            } else {
                final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getLoggerName());
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

    private static final String INTERNAL_LOGGER_NAME = JTestMeLogger.class.getName();

    private static String getLoggerName() {
        String className = INTERNAL_LOGGER_NAME;
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace != null && stackTrace.length > 1) {
            int index = 1;
            while (stackTrace[index].getClassName().equals(JTestMeLogger.class.getName())
                    && index < stackTrace.length - 1) {
                index++;
            }
            className = index < stackTrace.length - 1 ? stackTrace[index].getClassName() : className;
        }
        return className;
    }
}
