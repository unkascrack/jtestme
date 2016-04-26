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

    private static final long DEFAULT_PERIOD = 10L;
    private static final long ONE_MINUTE_SECONDS = 60L;
    private static final ViewerType DEFAULT_VIEWER_TYPE = ViewerType.TXT;

    private final ScheduledExecutorService service;

    private boolean running = false;
    private long period = DEFAULT_PERIOD;
    private ViewerType viewer = DEFAULT_VIEWER_TYPE;

    public static JTestMeScheduler getInstance() {
        return INSTANCE;
    }

    private JTestMeScheduler() {
        this.service = Executors.newSingleThreadScheduledExecutor();
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setPeriod(final long periodInMinutes) {
        this.period = periodInMinutes > 0 ? periodInMinutes : DEFAULT_PERIOD;
    }

    public void setPeriod(final String periodInMinutes) {
        try {
            setPeriod(Long.parseLong(periodInMinutes));
        } catch (final Throwable e) {
            this.period = DEFAULT_PERIOD;
        }
    }

    public void setViewer(final String viewerType) {
        this.viewer = ViewerType.toType(viewerType, DEFAULT_VIEWER_TYPE);
    }

    /**
     * Running schedule
     */
    public void start() {
        if (this.running) {
            stop();
        }
        this.service.scheduleAtFixedRate(new CollectorTask(), this.period * ONE_MINUTE_SECONDS,
                this.period * ONE_MINUTE_SECONDS, TimeUnit.SECONDS);
        this.running = true;
    }

    /**
     * Stop schedule
     */
    public void stop() {
        if (this.running) {
            this.service.shutdown();
            this.running = false;
        }
    }

    class CollectorTask implements Runnable {

        private final Viewer viewer;
        private boolean lastTaskWasError = false;

        CollectorTask() {
            this.viewer = ViewerFactory.loadViewer(JTestMeScheduler.this.viewer);
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

            if (error && !this.lastTaskWasError) {
                JTestMeLogger.error(this.viewer.getContentViewer(errors));
            }
            this.lastTaskWasError = error;
        }
    }
}
