package es.map.jtestme.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import es.map.jtestme.JTestMeFactory;

public class JTestMeFilter implements Filter {

    private static Logger logger = Logger.getAnonymousLogger();

    private static final String PARAM_CONFIG_LOCATION = "config-location";

    public void init(final FilterConfig config) throws ServletException {
        logger.fine("JTestMe filter init started");

        final long start = System.currentTimeMillis();
        final String configLocation = config.getInitParameter(PARAM_CONFIG_LOCATION);
        JTestMeFactory.loadExecutors(configLocation);
        final long duration = System.currentTimeMillis() - start;
        logger.fine("JTestMe filter init done in " + duration + " ms");
    }

    public void destroy() {
        final long start = System.currentTimeMillis();

        final long duration = System.currentTimeMillis() - start;
        logger.fine("JTestMe filter destroy done in " + duration + " ms");
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final long start = System.currentTimeMillis();

        final long duration = System.currentTimeMillis() - start;
        logger.fine("JTestMe filter doFilter done in " + duration + " ms");
    }
}
