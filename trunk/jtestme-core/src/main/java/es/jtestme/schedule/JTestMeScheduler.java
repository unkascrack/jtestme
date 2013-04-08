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

public class JTestMeScheduler {

    private static final JTestMeScheduler INSTANCE = new JTestMeScheduler();

    public static final Long DEFAULT_PERIOD = 60l;

    private boolean running = false;
    private final ScheduledExecutorService service;

    public static final JTestMeScheduler getInstance() {
        return INSTANCE;
    }

    private JTestMeScheduler() {
        service = Executors.newSingleThreadScheduledExecutor();
    }

    public boolean isRunning() {
        return running;
    }

    public void start() {
        start(DEFAULT_PERIOD);
    }

    public void start(final long periodInMinutes) {
        if (running) {
            stop();
        }
        service.scheduleAtFixedRate(new CollectorTask(), periodInMinutes, periodInMinutes, TimeUnit.SECONDS);
        running = true;
    }

    public void stop() {
        if (running) {
            service.shutdown();
            running = false;
        }
    }

    private static final ViewerType DEFAULT_VIEWER_TYPE = ViewerType.TXT;

    class CollectorTask implements Runnable {

        final Viewer viewer;
        private boolean lasTaskWasError = false;

        CollectorTask() {
            viewer = ViewerFactory.loadViewer(DEFAULT_VIEWER_TYPE);
        }

        public void run() {
            JTestMeLogger.info("Running scheduler...");
            boolean error = false;
            final List<VerificatorResult> errors = new ArrayList<VerificatorResult>();
            for (final VerificatorResult result : JTestMeBuilder.getInstance().executeVerificators()) {
                if (!result.isSuccess()) {
                    error = true;
                    errors.add(result);
                }
            }

            if (error && !lasTaskWasError) {
                final Viewer viewer = ViewerFactory.loadViewer(DEFAULT_VIEWER_TYPE);
                JTestMeLogger.error(viewer.getContentViewer(errors));
                lasTaskWasError = true;
            }
        }
    }

}
