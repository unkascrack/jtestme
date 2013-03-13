package es.jtestme.executors;

public enum JTestMeExecutorType {

    CONNECTION,
    CUSTOM,
    DATASOURCE,
    GRAPHICS,
    JDBC,
    JNDI,
    LDAP,
    OPENOFFICE,
    SMTP,
    WEBSERVICE;

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
