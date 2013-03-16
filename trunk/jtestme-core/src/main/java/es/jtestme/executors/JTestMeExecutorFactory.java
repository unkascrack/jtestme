package es.jtestme.executors;

import java.util.Map;

import es.jtestme.executors.impl.JTestMeDefaultExecutor;
import es.jtestme.logger.JTestMeLogger;
import es.jtestme.utils.JTestMeUtils;

public final class JTestMeExecutorFactory {

    private final static JTestMeExecutorFactory INSTANCE = new JTestMeExecutorFactory();

    private JTestMeExecutorFactory() {
    }

    /**
     * @return
     */
    public static final JTestMeExecutorFactory getInstance() {
        return INSTANCE;
    }

    /**
     * @param name
     * @param params
     * @return
     */
    public JTestMeExecutor loadExecutor(final String name, final Map<String, String> params) {
        final JTestMeExecutorType executorType = getExecutorType(name, params);
        JTestMeExecutor executor = null;
        if (executorType != null) {
            try {
                final String executorClassName = executorType.getExecutorClassName();
                executor = JTestMeUtils.loadExecutor(executorClassName, name, params);
                JTestMeLogger.info("JTestMe loading executor '" + name + "' of type: " + executorType);
            } catch (final Throwable e) {
                JTestMeLogger.warn("JTestMe could not load executor '" + name + "' of type: " + executorType);
            }
        }
        return executor;
    }

    /**
     * @param name
     * @param params
     * @return
     */
    private JTestMeExecutorType getExecutorType(final String name, final Map<String, String> params) {
        JTestMeExecutorType executorType = null;
        if (params == null || !params.containsKey(JTestMeDefaultExecutor.PARAM_TYPE)) {
            JTestMeLogger.warn("JTestMe could not load executor '" + name + "' no typedef.");
        } else {
            final String type = params.get(JTestMeDefaultExecutor.PARAM_TYPE);
            executorType = JTestMeExecutorType.toType(type);
            if (executorType == null) {
                JTestMeLogger.warn("JTestMe could not load executor '" + name + "' of type: " + type);
            }
        }
        return executorType;
    }
}
