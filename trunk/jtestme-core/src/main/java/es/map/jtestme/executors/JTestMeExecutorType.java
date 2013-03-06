package es.map.jtestme.executors;

public enum JTestMeExecutorType {

    JDBC,
    JNDI,
    CONNECTION,
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
