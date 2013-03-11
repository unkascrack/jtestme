package es.jtestme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import es.jtestme.config.JTestMeConfiguration;
import es.jtestme.domain.JTestMeResult;
import es.jtestme.executors.JTestMeExecutor;
import es.jtestme.executors.JTestMeExecutorFactory;
import es.jtestme.logger.JTestMeLogger;

public final class JTestMeBuilder {

    private static final JTestMeBuilder INSTANCE = new JTestMeBuilder();
    private static final List<JTestMeExecutor> executors = new ArrayList<JTestMeExecutor>();

    private JTestMeBuilder() {
    }

    public static final JTestMeBuilder getInstance() {
        return INSTANCE;
    }

    /**
     * 
     */
    public void destroy() {
        executors.clear();
    }

    /**
     * @param configLocation
     * @return
     */
    public void loadExecutors(final String configLocation) {
        final JTestMeConfiguration configuration = JTestMeConfiguration.getInstance();
        final Map<String, Map<String, String>> params = configuration.loadConfiguration(configLocation);
        if (params != null && !params.isEmpty()) {
            final JTestMeExecutorFactory executorFactory = JTestMeExecutorFactory.getInstance();
            for (final Entry<String, Map<String, String>> entry : params.entrySet()) {
                final String name = entry.getKey();
                final Map<String, String> properties = entry.getValue();
                final JTestMeExecutor executor = executorFactory.loadExecutor(name, properties);
                if (executor != null) {
                    executors.add(executor);
                }
            }
        }
    }

    /**
     * @return
     */
    public void addExecutor(final JTestMeExecutor executor) {
        executors.add(executor);
    }

    /**
     * @return
     */
    public List<JTestMeResult> runExecutors() {
        final List<JTestMeResult> results = new ArrayList<JTestMeResult>();
        for (final JTestMeExecutor executor : executors) {
            try {
                results.add(executor.executeTestMe());
            } catch (final Throwable e) {
                JTestMeLogger.warn("JTestMeBuilder loading executor '" + executor.getName() + "': " + e.getMessage());
            }
        }
        return results;
    }

    /**
     * @param executorName
     * @return
     */
    public JTestMeResult runExecutor(final String executorName) {
        final JTestMeExecutor executorSearch = getExecutor(executorName);
        return executorSearch != null ? executorSearch.executeTestMe() : null;
    }

    /**
     * @param executorName
     * @return
     */
    public JTestMeExecutor getExecutor(final String executorName) {
        JTestMeExecutor executorSearch = null;
        for (final JTestMeExecutor executor : executors) {
            if (executorName != null && executorName.equalsIgnoreCase(executor.getName())) {
                executorSearch = executor;
                break;
            }
        }
        return executorSearch;
    }
}