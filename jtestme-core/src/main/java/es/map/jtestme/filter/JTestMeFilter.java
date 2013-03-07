package es.map.jtestme.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import es.map.jtestme.JTestMeBuilder;
import es.map.jtestme.domain.JTestMeResult;
import es.map.jtestme.logger.JTestMeLogger;
import es.map.jtestme.viewer.JTestMeViewer;
import es.map.jtestme.viewer.JTestMeViewerFactory;
import es.map.jtestme.viewer.JTestMeViewerType;

/**
 * 
 * 
 * @author Carlos
 */
public class JTestMeFilter implements Filter {

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
     * FilterConfig param to define de name of the request parameter that
     * represents the type format of the response.
     */
    public static final String PARAM_CONFIG_PARAMETER_FORMAT = "param-type-format";

    /**
     * FilterConfig param to define the default filename of the response
     */
    public static final String PARAM_CONFIG_FILENAME = "filename";

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
        final long start = System.currentTimeMillis();
        doMonitoring(request, response);
        final long duration = System.currentTimeMillis() - start;
        JTestMeLogger.info("JTestMe filter doFilter done in " + duration + " ms");
    }

    /**
     * @param request
     * @param response
     */
    private void doMonitoring(final ServletRequest request, final ServletResponse response) {
        PrintWriter out = null;
        try {
            final List<JTestMeResult> results = BUILDER.runExecutors();
            final JTestMeViewerType viewerType = getViewerType(request);
            final JTestMeViewer viewer = JTestMeViewerFactory.loadViewer(viewerType);
            response.setContentType(viewer.getContentType());
            response.setCharacterEncoding(getEncoding());
            if (!JTestMeViewerType.isHTML(viewerType)) {
                final String fileName = getFileName(viewer);
                ((HttpServletResponse) response).setHeader("Content-Disposition", "inline; filename=\"" + fileName
                        + "\"");
            }
            out = response.getWriter();
            out.println(viewer.getContentViewer(results));
            out.flush();
        } catch (final Throwable e) {
            JTestMeLogger.warn("JTestMeFilter fail doMonitoring: " + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static final String DEFAULT_FILENAME = "jtestme";

    private String getFileName(final JTestMeViewer viewer) {
        final String fileName = config.getInitParameter(PARAM_CONFIG_FILENAME);
        return (fileName != null && fileName.trim().length() > 0 ? fileName : DEFAULT_FILENAME) + viewer.getExtension();
    }

    private static final String DEFAULT_REQUEST_PARAM_FORMAT = "format";

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
    public String getEncoding() {
        final String encoding = config.getInitParameter(PARAM_CONFIG_ENCODING);
        return encoding != null && encoding.trim().length() > 0 ? encoding : DEFAULT_ENCODING;
    }
}
