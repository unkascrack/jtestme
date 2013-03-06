package es.map.jtestme.executors;

import java.util.Map;
import java.util.logging.Logger;

import es.map.jtestme.executors.impl.ConnectionExecutor;
import es.map.jtestme.executors.impl.CustomExecutor;
import es.map.jtestme.executors.impl.JDBCExecutor;
import es.map.jtestme.executors.impl.JNDIExecutor;
import es.map.jtestme.executors.impl.JTestMeDefaultExecutor;

public class JTestMeExecutorFactory {

    private static final Logger logger = Logger.getAnonymousLogger();

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
        final String type = params.get(JTestMeDefaultExecutor.PARAM_TYPE);
        logger.info("JTestMe loading executor '" + name + "' of type: " + type);
        final JTestMeExecutor executor;
        switch (JTestMeExecutorType.toType(type)) {
            case JDBC:
                executor = new JDBCExecutor(params);
            break;
            case JNDI:
                executor = new JNDIExecutor(params);
            break;
            case CONNECTION:
                executor = new ConnectionExecutor(params);
            break;
            case CUSTOM:
                executor = new CustomExecutor(params);
            break;
            default:
                logger.warning("JTestMe could not load executor '" + name + "' of type: " + type);
                executor = null;
            break;
        }
        return executor;
    }
}
