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
import es.jtestme.domain.JTestMeResult;
import es.jtestme.logger.JTestMeLogger;
import es.jtestme.viewer.JTestMeViewer;
import es.jtestme.viewer.JTestMeViewerFactory;
import es.jtestme.viewer.JTestMeViewerType;

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
     * Instance of FilterConfig
     */
    private FilterConfig config;

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(final FilterConfig config) throws ServletException {
        final long start = System.currentTimeMillis();
        this.config = config;
        JTestMeLogger.loggerEnabled(Boolean.parseBoolean(config.getInitParameter(PARAM_CONFIG_LOG)));
        final String configLocation = config.getInitParameter(PARAM_CONFIG_LOCATION);
        BUILDER.loadExecutors(configLocation);
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
            final List<JTestMeResult> results = BUILDER.runExecutors();
            final JTestMeViewerType viewerType = getViewerType(request);
            final JTestMeViewer viewer = JTestMeViewerFactory.loadViewer(viewerType);
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
    private JTestMeViewerType getViewerType(final ServletRequest request) {
        String requestParam = config.getInitParameter(PARAM_CONFIG_PARAMETER_FORMAT);
        requestParam = requestParam != null && requestParam.trim().length() > 0 ? requestParam
                : DEFAULT_REQUEST_PARAM_FORMAT;
        return JTestMeViewerType.toType(request.getParameter(requestParam), JTestMeViewerType.HTML);
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
