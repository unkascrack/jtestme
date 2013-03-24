package es.jtestme.filter;

import javax.servlet.FilterConfig;

import es.jtestme.JTestMeBuilder;
import es.jtestme.logger.JTestMeLogger;
import es.jtestme.viewers.ViewerFactory;
import es.jtestme.viewers.ViewerType;

public final class Parameters {

    private Parameters() {
    }

    /**
     * 
     */
    private static FilterConfig filterConfig;

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
        filterConfig = config;

        initializeLogger();
        initializeVerificators();
        initializeViewers();
    }

    private static void initializeLogger() {
        JTestMeLogger.info("JTestMe initializate logger...");
        JTestMeLogger.loggerEnabled(Boolean.parseBoolean(getInitParameter(ParameterType.LOG, "true")));
    }

    private static void initializeVerificators() {
        JTestMeLogger.info("JTestMe initializate verificators...");
        final String configLocation = getInitParameter(ParameterType.CONFIG_LOCATION);
        JTestMeBuilder.getInstance().loadVerificators(configLocation);
    }

    private static void initializeViewers() {
        JTestMeLogger.info("JTestMe initializate viewers...");
        ViewerFactory.registerViewer(ViewerType.HTML, getInitParameter(ParameterType.HTML_VIEWER_CLASS));
        ViewerFactory.registerViewer(ViewerType.JSON, getInitParameter(ParameterType.JSON_VIEWER_CLASS));
        ViewerFactory.registerViewer(ViewerType.TXT, getInitParameter(ParameterType.TXT_VIEWER_CLASS));
        ViewerFactory.registerViewer(ViewerType.XML, getInitParameter(ParameterType.XML_VIEWER_CLASS));
        ViewerFactory.registerViewer(ViewerType.CUSTOM, getInitParameter(ParameterType.CUSTOM_VIEWER_CLASS));
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
