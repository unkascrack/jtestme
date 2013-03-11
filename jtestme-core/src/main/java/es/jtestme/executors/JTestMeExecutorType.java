package es.jtestme.executors;

public enum JTestMeExecutorType {

    JDBC,
    DATASOURCE,
    JNDI,
    CONNECTION,
    LDAP,
    OPENOFFICE,
    SMTP,
    WEBSERVICE,
    CUSTOM;

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
