package es.map.jtestme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import es.map.jtestme.config.JTestMeConfiguration;
import es.map.jtestme.domain.JTestMeResult;
import es.map.jtestme.executors.JTestMeExecutor;
import es.map.jtestme.executors.JTestMeExecutorFactory;
import es.map.jtestme.logger.JTestMeLogger;

public class JTestMeBuilder {

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
        if (executors.isEmpty()) {
            synchronized (executors) {
                if (executors.isEmpty()) {
                    final JTestMeConfiguration configuration = JTestMeConfiguration.getInstance();
                    final Map<String, Map<String, String>> params = configuration.loadConfiguration(configLocation);
                    if (!params.isEmpty()) {
                        final JTestMeExecutorFactory executorFactory = JTestMeExecutorFactory.getInstance();
                        for (final Entry<String, Map<String, String>> entry : params.entrySet()) {
                            final String name = entry.getKey();
                            final Map<String, String> properties = entry.getValue();
                            executors.add(executorFactory.loadExecutor(name, properties));
                        }
                    }
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
}
