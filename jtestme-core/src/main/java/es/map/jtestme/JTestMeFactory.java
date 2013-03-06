package es.map.jtestme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import es.map.jtestme.config.JTestMeConfiguration;
import es.map.jtestme.executors.JTestMeExecutor;
import es.map.jtestme.executors.JTestMeExecutorFactory;

public class JTestMeFactory {

    private static final List<JTestMeExecutor> executors = new ArrayList<JTestMeExecutor>();

    private JTestMeFactory() {
    }

    /**
     * @param configLocation
     * @return
     */
    public static void loadExecutors(final String configLocation) {
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
    public static List<JTestMeExecutor> getExecutors() {
        return executors;
    }

}
