package es.map.jtestme.executors;

import java.util.Map;

import es.map.jtestme.executors.impl.ConnectionExecutor;
import es.map.jtestme.executors.impl.CustomExecutor;
import es.map.jtestme.executors.impl.DatasourceExecutor;
import es.map.jtestme.executors.impl.JDBCExecutor;
import es.map.jtestme.executors.impl.JNDIExecutor;
import es.map.jtestme.executors.impl.JTestMeDefaultExecutor;
import es.map.jtestme.executors.impl.LDAPExecutor;
import es.map.jtestme.executors.impl.WebServiceExecutor;
import es.map.jtestme.logger.JTestMeLogger;

public class JTestMeExecutorFactory {

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
        JTestMeExecutor executor = null;
        if (params == null || !params.containsKey(JTestMeDefaultExecutor.PARAM_TYPE)) {
            JTestMeLogger.warn("JTestMe could not load executor '" + name + "' no typedef.");
        } else {
            final String type = params.get(JTestMeDefaultExecutor.PARAM_TYPE);
            final JTestMeExecutorType executorType = JTestMeExecutorType.toType(type);
            if (executorType == null) {
                JTestMeLogger.warn("JTestMe could not load executor '" + name + "' of type: " + type);
            } else {
                JTestMeLogger.info("JTestMe loading executor '" + name + "' of type: " + executorType);
                switch (executorType) {
                    case JDBC:
                        executor = new JDBCExecutor(params);
                    break;
                    case DATASOURCE:
                        executor = new DatasourceExecutor(params);
                    break;
                    case JNDI:
                        executor = new JNDIExecutor(params);
                    break;
                    case CONNECTION:
                        executor = new ConnectionExecutor(params);
                    break;
                    case LDAP:
                        executor = new LDAPExecutor(params);
                    break;
                    case WEBSERVICE:
                        executor = new WebServiceExecutor(params);
                    break;
                    case CUSTOM:
                        executor = new CustomExecutor(params);
                    break;
                    default:
                        JTestMeLogger.warn("JTestMe could not load executor '" + name + "' of type: " + executorType);
                    break;
                }
            }
        }
        return executor;
    }
}
