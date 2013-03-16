package es.jtestme.executors;

import es.jtestme.executors.impl.ConnectionExecutor;
import es.jtestme.executors.impl.CustomExecutor;
import es.jtestme.executors.impl.DatasourceExecutor;
import es.jtestme.executors.impl.FTPExecutor;
import es.jtestme.executors.impl.GraphicsExecutor;
import es.jtestme.executors.impl.JDBCExecutor;
import es.jtestme.executors.impl.JNDIExecutor;
import es.jtestme.executors.impl.LDAPExecutor;
import es.jtestme.executors.impl.OpenOfficeExecutor;
import es.jtestme.executors.impl.SMTPExecutor;
import es.jtestme.executors.impl.WebServiceExecutor;

public enum JTestMeExecutorType {

    CONNECTION(ConnectionExecutor.class),
    CUSTOM(CustomExecutor.class),
    DATASOURCE(DatasourceExecutor.class),
    FTP(FTPExecutor.class),
    GRAPHICS(GraphicsExecutor.class),
    JDBC(JDBCExecutor.class),
    JNDI(JNDIExecutor.class),
    LDAP(LDAPExecutor.class),
    OPENOFFICE(OpenOfficeExecutor.class),
    SMTP(SMTPExecutor.class),
    WEBSERVICE(WebServiceExecutor.class);

    private Class<? extends JTestMeExecutor> executorClass;

    private JTestMeExecutorType(final Class<? extends JTestMeExecutor> executorClass) {
        this.executorClass = executorClass;
    }

    public Class<? extends JTestMeExecutor> getExecutorClass() {
        return executorClass;
    }

    public String getExecutorClassName() {
        return executorClass.getName();
    }

    public static JTestMeExecutorType toType(final String str) {
        JTestMeExecutorType type = null;
        try {
            type = str != null ? valueOf(str.toUpperCase()) : null;
        } catch (final IllegalArgumentException e) {
            type = null;
        }
        return type;
    }
}
