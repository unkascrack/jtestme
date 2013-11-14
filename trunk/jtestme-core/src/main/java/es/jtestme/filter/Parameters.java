package es.jtestme.filter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.FilterConfig;

import es.jtestme.JTestMeBuilder;
import es.jtestme.logger.JTestMeLogger;
import es.jtestme.schedule.JTestMeScheduler;
import es.jtestme.utils.JTestMeUtils;
import es.jtestme.viewers.ViewerFactory;
import es.jtestme.viewers.ViewerType;

public final class Parameters {

    private Parameters() {
    }

    /**
     * 
     */
    private static boolean initialized;

    /**
     * 
     */
    private static FilterConfig filterConfig;

    /**
     * 
     */
    private static String jTestMeVersion;

    /**
     * @return
     */
    public static String getjTestMeVersion() {
        return jTestMeVersion;
    }

    /**
     * @return
     */
    public static final String getContext() {
        return filterConfig.getServletContext().getContextPath();
    }

    /**
     * @return
     */
    public static final String getServerInfo() {
        return filterConfig.getServletContext().getServerInfo();
    }

    /**
     * @return
     */
    public static final String getEncoding() {
        return getInitParameter(ParameterType.ENCODING, "UTF-8");
    }

    /**
     * @return
     */
    public static final String getRequestParamFormat() {
        return getInitParameter(ParameterType.PARAMETER_FORMAT, "format");
    }

    /**
     * @return
     */
    public static final ViewerType getDefaultViewer() {
        return ViewerType.toType(getInitParameter(ParameterType.DEFAULT_VIEWER, "html"));
    }

    /**
     * @param resource
     * @return
     */
    public static final String getMimeType(final String resource) {
        return filterConfig.getServletContext().getMimeType(resource);
    }

    /**
     * @param config
     */
    static final void initialize(final FilterConfig config) {
        if (initialized) {
            JTestMeLogger.warn("JTestMe was initialized before...");
        } else if (!initialized) {
            synchronized (Parameters.class) {
                if (!initialized) {
                    filterConfig = config;
                    initializeLogger();
                    initializeVersion();
                    initializeVerificators();
                    initializeViewers();
                    initializeScheduler();
                    initialized = true;
                }
            }
        }
    }

    static final void destroy() {
        destroyVerificators();
        destroyScheduler();
    }

    private static void initializeLogger() {
        JTestMeLogger.debug("JTestMe initializate logger...");
        JTestMeLogger.setLoggerEnabled(Boolean.parseBoolean(getInitParameter(ParameterType.LOG, "true")));
    }

    private static void initializeVersion() {
        InputStream input = null;
        try {
            input = new BufferedInputStream(Parameters.class.getResourceAsStream("/META-INF/version.properties"));
            if (input != null) {
                final Properties properties = new Properties();
                properties.load(input);
                jTestMeVersion = properties.getProperty("jtestme.version");
                JTestMeLogger.debug("JTestMe version: " + jTestMeVersion);
            }
        } catch (final IOException e) {
            JTestMeLogger.warn("JTestMe error loading version: " + e.getMessage());
        } finally {
            JTestMeUtils.closeQuietly(input);
        }
    }

    private static void initializeVerificators() {
        JTestMeLogger.debug("JTestMe initializate verificators...");
        final String configLocation = getInitParameter(ParameterType.CONFIG_LOCATION);
        JTestMeBuilder.getInstance().loadVerificators(configLocation);
    }

    private static void initializeViewers() {
        JTestMeLogger.debug("JTestMe initializate viewers...");
        ViewerFactory.registerViewer(ViewerType.HTML, getInitParameter(ParameterType.HTML_VIEWER_CLASS));
        ViewerFactory.registerViewer(ViewerType.JSON, getInitParameter(ParameterType.JSON_VIEWER_CLASS));
        ViewerFactory.registerViewer(ViewerType.TXT, getInitParameter(ParameterType.TXT_VIEWER_CLASS));
        ViewerFactory.registerViewer(ViewerType.XML, getInitParameter(ParameterType.XML_VIEWER_CLASS));
        ViewerFactory.registerViewer(ViewerType.CUSTOM, getInitParameter(ParameterType.CUSTOM_VIEWER_CLASS));
    }

    private static void initializeScheduler() {
        final boolean scheduleEnabled = Boolean.parseBoolean(getInitParameter(ParameterType.SCHEDULE, "false"));
        if (scheduleEnabled) {
            JTestMeLogger.debug("JTestMe starting scheduler...");
            final JTestMeScheduler scheduler = JTestMeScheduler.getInstance();
            scheduler.setPeriod(getInitParameter(ParameterType.SCHEDULE_PERIOD));
            scheduler.setViewer(getInitParameter(ParameterType.SCHEDULE_VIEWER));
            scheduler.start();
        }
    }

    private static void destroyVerificators() {
        JTestMeLogger.debug("JTestMe destroy verificators...");
        JTestMeBuilder.getInstance().destroy();
    }

    private static void destroyScheduler() {
        if (JTestMeScheduler.getInstance().isRunning()) {
            JTestMeLogger.debug("JTestMe stopping scheduler...");
            JTestMeScheduler.getInstance().stop();
        }
    }

    private static final String getInitParameter(final ParameterType parameter) {
        return getInitParameter(parameter, null);
    }

    private static final String getInitParameter(final ParameterType parameter, final String defaultValue) {
        if (filterConfig == null) {
            throw new IllegalAccessError("Parameters must be initializate before request parametes.");
        }
        final String value = filterConfig.getInitParameter(parameter.getCode());
        return value != null && value.trim().length() > 0 ? value : defaultValue;
    }
}
