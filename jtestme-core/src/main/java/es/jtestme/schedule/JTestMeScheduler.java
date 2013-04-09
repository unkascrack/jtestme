package es.jtestme.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import es.jtestme.JTestMeBuilder;
import es.jtestme.domain.VerificatorResult;
import es.jtestme.logger.JTestMeLogger;
import es.jtestme.viewers.Viewer;
import es.jtestme.viewers.ViewerFactory;
import es.jtestme.viewers.ViewerType;

public final class JTestMeScheduler {

    private static final JTestMeScheduler INSTANCE = new JTestMeScheduler();

    private static final long DEFAULT_PERIOD = 60l;
    private static final ViewerType DEFAULT_VIEWER_TYPE = ViewerType.TXT;

    private final ScheduledExecutorService service;

    private boolean running = false;
    private long period = DEFAULT_PERIOD;
    private ViewerType viewer = DEFAULT_VIEWER_TYPE;

    public static final JTestMeScheduler getInstance() {
        return INSTANCE;
    }

    private JTestMeScheduler() {
        service = Executors.newSingleThreadScheduledExecutor();
    }

    public boolean isRunning() {
        return running;
    }

    public void setPeriod(final long periodInMinutes) {
        period = periodInMinutes > 0 ? periodInMinutes : DEFAULT_PERIOD;
    }

    public void setPeriod(final String periodInMinutes) {
        try {
            setPeriod(Long.parseLong(periodInMinutes));
        } catch (final Throwable e) {
            period = DEFAULT_PERIOD;
        }
    }

    public void setViewer(final String viewerType) {
        viewer = ViewerType.toType(viewerType, DEFAULT_VIEWER_TYPE);
    }

    /**
     * Running schedule
     */
    public void start() {
        if (running) {
            stop();
        }
        service.scheduleAtFixedRate(new CollectorTask(), period * 5l, period * 5l, TimeUnit.SECONDS);
        running = true;
    }

    /**
     * Stop schedule
     */
    public void stop() {
        if (running) {
            service.shutdown();
            running = false;
        }
    }

    class CollectorTask implements Runnable {

        final Viewer viewer;
        private boolean lastTaskWasError = false;

        CollectorTask() {
            viewer = ViewerFactory.loadViewer(JTestMeScheduler.this.viewer);
        }

        public void run() {
            JTestMeLogger.debug("Running scheduler...");
            boolean error = false;
            final List<VerificatorResult> errors = new ArrayList<VerificatorResult>();
            for (final VerificatorResult result : JTestMeBuilder.getInstance().executeVerificators()) {
                if (!result.isSuccess()) {
                    error = true;
                    errors.add(result);
                }
            }

            if (error && !lastTaskWasError) {
                JTestMeLogger.error(viewer.getContentViewer(errors));
            }
            lastTaskWasError = error;
        }
    }
}
