package es.jtestme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import es.jtestme.config.ConfigurationBuilder;
import es.jtestme.domain.VerificatorResult;
import es.jtestme.logger.JTestMeLogger;
import es.jtestme.verificators.Verificator;
import es.jtestme.verificators.VerificatorFactory;

public final class JTestMeBuilder {

    private static final int MAX_THREADS = 8;

    private static final JTestMeBuilder INSTANCE = new JTestMeBuilder();
    private static final List<Verificator> verificators = new ArrayList<Verificator>();

    private JTestMeBuilder() {
    }

    public static final JTestMeBuilder getInstance() {
        return INSTANCE;
    }

    /**
     * 
     */
    public void destroy() {
        verificators.clear();
    }

    /**
     * @param configLocation
     * @return
     */
    public void loadVerificators(final String configLocation) {
        final ConfigurationBuilder configuration = ConfigurationBuilder.getInstance();
        final Map<String, Map<String, String>> params = configuration.loadConfiguration(configLocation);
        if (params != null && !params.isEmpty()) {
            final VerificatorFactory verificatorFactory = VerificatorFactory.getInstance();
            for (final Entry<String, Map<String, String>> entry : params.entrySet()) {
                final String name = entry.getKey();
                final Map<String, String> properties = entry.getValue();
                final Verificator verificator = verificatorFactory.loadVerificator(name, properties);
                if (verificator != null) {
                    verificators.add(verificator);
                }
            }
        }
    }

    /**
     * @param verificator
     */
    public void addVerificator(final Verificator verificator) throws IllegalArgumentException {
        if (verificator == null) {
            throw new IllegalArgumentException("Verificator must not be null.");
        }
        verificators.add(verificator);
    }

    /**
     * @return
     */
    public List<VerificatorResult> executeVerificators() {
        final List<VerificatorResult> results = new ArrayList<VerificatorResult>();
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(MAX_THREADS);
            final Set<Callable<VerificatorResult>> callables = new HashSet<Callable<VerificatorResult>>();
            for (final Verificator verificator : verificators) {
                callables.add(new Callable<VerificatorResult>() {
                    public VerificatorResult call() throws Exception {
                        return executeVerificator(verificator);
                    }
                });
            }

            final Collection<Future<VerificatorResult>> futures = executorService.invokeAll(callables);
            for (final Future<VerificatorResult> future : futures) {
                final VerificatorResult result = future.get();
                if (result != null) {
                    results.add(result);
                }
            }

        } catch (final Throwable e) {
            JTestMeLogger.warn("JTestMeBuilder executing verificators: " + e.getMessage(), e);
        } finally {
            executorService.shutdown();
        }
        return results;
    }

    /**
     * @param verificatorUid
     * @return
     */
    public VerificatorResult executeVerificator(final String verificatorUid) {
        final Verificator verificator = searchVerificator(verificatorUid);
        return verificator != null ? executeVerificator(verificator) : null;
    }

    /**
     * @param verificator
     * @return
     */
    private VerificatorResult executeVerificator(final Verificator verificator) {
        VerificatorResult result = null;
        try {
            result = verificator.execute();
        } catch (final Throwable e) {
            JTestMeLogger.warn(
                    "JTestMeBuilder executing verificator '" + verificator.getUid() + "': " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @param verificatorUid
     * @return
     */
    private Verificator searchVerificator(final String verificatorUid) {
        Verificator verificatorSearch = null;
        if (verificatorUid != null) {
            for (final Verificator verificator : verificators) {
                if (verificatorUid.equalsIgnoreCase(verificator.getUid())) {
                    verificatorSearch = verificator;
                    break;
                }
            }
        }
        return verificatorSearch;
    }
}
