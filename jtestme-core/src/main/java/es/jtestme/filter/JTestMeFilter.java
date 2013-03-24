package es.jtestme.filter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import es.jtestme.JTestMeBuilder;
import es.jtestme.domain.VerificatorResult;
import es.jtestme.logger.JTestMeLogger;
import es.jtestme.viewers.Viewer;
import es.jtestme.viewers.ViewerFactory;
import es.jtestme.viewers.ViewerType;

public final class JTestMeFilter implements Filter {

    private static final JTestMeBuilder BUILDER = JTestMeBuilder.getInstance();

    /**
     * FilterConfig param to indicate the path to configuration file.
     */
    public static final String PARAM_CONFIG_LOCATION = "config-location";

    /**
     * FilterConfig param to define the encoding of the response.
     */
    public static final String PARAM_CONFIG_ENCODING = "encoding";

    /**
     * FilterConfig param to define is active logger
     */
    public static final String PARAM_CONFIG_LOG = "log";

    /**
     * FilterConfig param to define de name of the request parameter that
     * represents the type format of the response.
     */
    public static final String PARAM_CONFIG_PARAMETER_FORMAT = "param-type-format";

    /**
     * FilterConfig param to define the default viewer: HTML, JSON, TXT, XML or CUSTOM, by default the value is HTML
     */
    public static final String PARAM_CONFIG_DEFAULT_VIEWER = "default-viewer";

    /**
     * FilterConfig param to define the class that implements the HTML viewer, by default is the class
     * es.jtestme.viewers.impl.HTMLViewer
     */
    public static final String PARAM_CONFIG_HTML_VIEWER_CLASS = "html-viewer-class";

    /**
     * FilterConfig param to define the class that implements the JSON viewer, by default is the class
     * es.jtestme.viewers.impl.JSONViewer
     */
    public static final String PARAM_CONFIG_JSON_VIEWER_CLASS = "json-viewer-class";

    /**
     * FilterConfig param to define the class that implements the TXT viewer, by default is the class
     * es.jtestme.viewers.impl.PlainTextViewer
     */
    public static final String PARAM_CONFIG_TXT_VIEWER_CLASS = "txt-viewer-class";

    /**
     * FilterConfig param to define the class that implements the XML viewer, by default is the class
     * es.jtestme.viewers.impl.XMLViewer
     */
    public static final String PARAM_CONFIG_XML_VIEWER_CLASS = "xml-viewer-class";

    /**
     * FilterConfig param to define the class that implements the CUSTOM viewer, by default is the class
     * es.jtestme.viewers.impl.CustomViewer
     */
    public static final String PARAM_CONFIG_CUSTOM_VIEWER_CLASS = "custom-viewer-class";

    /**
     * Instance of FilterConfig
     */
    private FilterConfig config;

    /**
     * 
     */
    private ViewerType defaultViewer;

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(final FilterConfig config) throws ServletException {
        final long start = System.currentTimeMillis();
        this.config = config;
        {
            JTestMeLogger.loggerEnabled(Boolean.parseBoolean(config.getInitParameter(PARAM_CONFIG_LOG)));
        }
        {
            BUILDER.loadVerificators(config.getInitParameter(PARAM_CONFIG_LOCATION));
        }

        {
            JTestMeLogger.info("JTestMe loading viewers...");
            defaultViewer = ViewerType.toType(config.getInitParameter(PARAM_CONFIG_DEFAULT_VIEWER), ViewerType.HTML);
            JTestMeLogger.debug("JTestMe default viewer: " + defaultViewer);

            ViewerFactory.registerViewer(ViewerType.HTML, config.getInitParameter(PARAM_CONFIG_HTML_VIEWER_CLASS));
            ViewerFactory.registerViewer(ViewerType.JSON, config.getInitParameter(PARAM_CONFIG_JSON_VIEWER_CLASS));
            ViewerFactory.registerViewer(ViewerType.TXT, config.getInitParameter(PARAM_CONFIG_TXT_VIEWER_CLASS));
            ViewerFactory.registerViewer(ViewerType.XML, config.getInitParameter(PARAM_CONFIG_XML_VIEWER_CLASS));
            ViewerFactory.registerViewer(ViewerType.CUSTOM, config.getInitParameter(PARAM_CONFIG_CUSTOM_VIEWER_CLASS));
        }

        final long duration = System.currentTimeMillis() - start;
        JTestMeLogger.info("JTestMe filter init done in " + duration + " ms");
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        final long start = System.currentTimeMillis();
        BUILDER.destroy();
        final long duration = System.currentTimeMillis() - start;
        JTestMeLogger.info("JTestMe filter destroy done in " + duration + " ms");
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        try {
            if (request.getParameter(RESOURCE_PARAMETER) == null) {
                doMonitoring(request, response);
            } else {
                doResource(request, response);
            }
        } catch (final Throwable e) {
            JTestMeLogger.warn("JTestMeFilter fail doFilter: " + e.getMessage());
        }
    }

    /**
     * @param request
     * @param response
     * @throws IOException
     */
    private void doMonitoring(final ServletRequest request, final ServletResponse response) throws IOException {
        final long start = System.currentTimeMillis();
        PrintWriter output = null;
        try {
            final List<VerificatorResult> results = BUILDER.executeVerificators();
            final ViewerType viewerType = getViewerType(request);
            final Viewer viewer = ViewerFactory.loadViewer(viewerType);
            response.setContentType(viewer.getContentType());
            response.setCharacterEncoding(getEncoding());
            output = response.getWriter();
            output.println(viewer.getContentViewer(results));
            output.flush();
        } finally {
            if (output != null) {
                output.close();
            }
        }
        final long duration = System.currentTimeMillis() - start;
        JTestMeLogger.info("JTestMe filter doFilter done in " + duration + " ms");
    }

    private static final String RESOURCE_PARAMETER = "resource";
    private static final String RESOURCE_FOLDER = "/META-INF/resources/jtestme/";

    /**
     * @param request
     * @param response
     * @throws IOException
     */
    private void doResource(final ServletRequest request, final ServletResponse response) throws IOException {
        final String resource = request.getParameter(RESOURCE_PARAMETER);
        final String localResource = RESOURCE_FOLDER + resource;

        if (getClass().getResource(localResource) != null) {
            ((HttpServletResponse) response).addHeader("Cache-Control", "max-age=3600");
            response.setContentType(config.getServletContext().getMimeType(resource));
            OutputStream output = null;
            InputStream input = null;

            try {
                input = new BufferedInputStream(getClass().getResourceAsStream(localResource));
                output = response.getOutputStream();

                final byte[] bytes = new byte[4 * 1024];
                int length = input.read(bytes);
                while (length != -1) {
                    output.write(bytes, 0, length);
                    length = input.read(bytes);
                }
            } finally {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            }
        }
    }

    private static final String DEFAULT_REQUEST_PARAM_FORMAT = "format";

    /**
     * @param request
     * @return
     */
    private ViewerType getViewerType(final ServletRequest request) {
        String requestParam = config.getInitParameter(PARAM_CONFIG_PARAMETER_FORMAT);
        requestParam = requestParam != null && requestParam.trim().length() > 0 ? requestParam
                : DEFAULT_REQUEST_PARAM_FORMAT;
        return ViewerType.toType(request.getParameter(requestParam), defaultViewer);
    }

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * @return
     */
    private String getEncoding() {
        final String encoding = config.getInitParameter(PARAM_CONFIG_ENCODING);
        return encoding != null && encoding.trim().length() > 0 ? encoding : DEFAULT_ENCODING;
    }
}
