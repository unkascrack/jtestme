package es.jtestme.executors;

import java.util.Map;

import es.jtestme.executors.impl.ConnectionExecutor;
import es.jtestme.executors.impl.CustomExecutor;
import es.jtestme.executors.impl.DatasourceExecutor;
import es.jtestme.executors.impl.JDBCExecutor;
import es.jtestme.executors.impl.JNDIExecutor;
import es.jtestme.executors.impl.JTestMeDefaultExecutor;
import es.jtestme.executors.impl.LDAPExecutor;
import es.jtestme.executors.impl.OpenOfficeExecutor;
import es.jtestme.executors.impl.SMTPExecutor;
import es.jtestme.executors.impl.WebServiceExecutor;
import es.jtestme.logger.JTestMeLogger;

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
        JTestMeExecutor executor = null;
        if (params == null || !params.containsKey(JTestMeDefaultExecutor.PARAM_TYPE)) {
            JTestMeLogger.warn("JTestMe could not load executor '" + name + "' no typedef.");
        } else {
            final String type = params.get(JTestMeDefaultExecutor.PARAM_TYPE);
            final JTestMeExecutorType executorType = JTestMeExecutorType.toType(type);
            if (executorType == null) {
                JTestMeLogger.warn("JTestMe could not load executor '" + name + "' of type: " + type);
            } else {
                try {
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
                        case OPENOFFICE:
                            executor = new OpenOfficeExecutor(params);
                        break;
                        case SMTP:
                            executor = new SMTPExecutor(params);
                        break;
                        case WEBSERVICE:
                            executor = new WebServiceExecutor(params);
                        break;
                        case CUSTOM:
                            executor = new CustomExecutor(params);
                        break;
                        default:
                            throw new IllegalArgumentException("JTestMe could not load executor '" + name
                                    + "' of type: " + executorType);
                    }
                    JTestMeLogger.info("JTestMe loading executor '" + name + "' of type: " + executorType);
                } catch (final Throwable e) {
                    JTestMeLogger.warn("JTestMe could not load executor '" + name + "' of type: " + executorType);
                }
            }
        }
        return executor;
    }
}
