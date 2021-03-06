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

    private static final int BUFFER_SIZE = 4 * 1024;

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(final FilterConfig config) throws ServletException {
        final long start = System.currentTimeMillis();
        Parameters.initialize(config);
        final long duration = System.currentTimeMillis() - start;
        JTestMeLogger.info("JTestMe filter init done in " + duration + " ms");
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        final long start = System.currentTimeMillis();
        Parameters.destroy();
        final long duration = System.currentTimeMillis() - start;
        JTestMeLogger.info("JTestMe filter destroy done in " + duration + " ms");
    }

    /*
     * (non-Javadoc)
     *
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
            final List<VerificatorResult> results = JTestMeBuilder.getInstance().executeVerificators();
            final ViewerType viewerType = getViewerType(request);
            final Viewer viewer = ViewerFactory.loadViewer(viewerType);
            response.setContentType(viewer.getContentType());
            response.setCharacterEncoding(Parameters.getEncoding());
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
            response.setContentType(Parameters.getMimeType(resource));
            OutputStream output = null;
            InputStream input = null;

            try {
                input = new BufferedInputStream(getClass().getResourceAsStream(localResource));
                output = response.getOutputStream();

                final byte[] bytes = new byte[BUFFER_SIZE];
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

    /**
     * @param request
     * @return
     */
    private ViewerType getViewerType(final ServletRequest request) {
        final String requestParamFormat = Parameters.getRequestParamFormat();
        final ViewerType defaultViewer = Parameters.getDefaultViewer();
        return ViewerType.toType(request.getParameter(requestParamFormat), defaultViewer);
    }
}
