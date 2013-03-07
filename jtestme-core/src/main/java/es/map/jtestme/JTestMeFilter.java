package es.map.jtestme;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import es.map.jtestme.domain.JTestMeResult;
import es.map.jtestme.viewer.JTestMeViewer;
import es.map.jtestme.viewer.JTestMeViewerFactory;

public class JTestMeFilter implements Filter {

    private static Logger logger = Logger.getAnonymousLogger();

    private static final JTestMeBuilder BUILDER = JTestMeBuilder.getInstance();

    private static final String PARAM_CONFIG_LOCATION = "config-location";
    private static final String PARAM_CONFIG_ENCODING = "encoding";
    private static final String PARAM_CONFIG_FILENAME = "filename";

    private String encoding;

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(final FilterConfig config) throws ServletException {
        final long start = System.currentTimeMillis();
        final String configLocation = config.getInitParameter(PARAM_CONFIG_LOCATION);
        encoding = config.getInitParameter(PARAM_CONFIG_ENCODING);
        BUILDER.loadExecutors(configLocation);
        final long duration = System.currentTimeMillis() - start;
        logger.fine("JTestMe filter init done in " + duration + " ms");
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        final long start = System.currentTimeMillis();
        BUILDER.destroy();
        final long duration = System.currentTimeMillis() - start;
        logger.fine("JTestMe filter destroy done in " + duration + " ms");
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final long start = System.currentTimeMillis();
        doMonitoring(request, response);
        final long duration = System.currentTimeMillis() - start;
        logger.fine("JTestMe filter doFilter done in " + duration + " ms");
    }

    /**
     * @param request
     * @param response
     */
    private void doMonitoring(final ServletRequest request, final ServletResponse response) {
        final List<JTestMeResult> results = BUILDER.runExecutors();
        final String type = request.getParameter("type");
        final JTestMeViewer viewer = JTestMeViewerFactory.loadViewer(type);
        PrintWriter out = null;
        try {
            response.setContentType(viewer.getContentType());
            response.setCharacterEncoding(getEncoding());
            out = response.getWriter();
            out.println(viewer.getContentViewer(results));

            // response.setHeader("Content-Disposition", "inline; filename=\"verificarSistema.xml\"");

            out.flush();
        } catch (final IOException e) {
            logger.severe("JTestMeFilter fail doMonitoring: " + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * @return
     */
    public String getEncoding() {
        return encoding != null && encoding.trim().length() > 0 ? encoding : DEFAULT_ENCODING;
    }
}
