package es.jtestme.utils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;

import es.jtestme.verificators.Verificator;

public final class JTestMeUtils {

    private JTestMeUtils() {
    }

    /**
     * @param className
     * @param arguments
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    public static <T> T loadClass(final String className, final Object... arguments) throws ClassNotFoundException,
            IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        T classType = null;
        if (className != null && className.trim().length() > 0) {
            final Class<?> clase = Class.forName(className);
            final Constructor<?>[] constructors = clase.getConstructors();
            for (final Constructor<?> constructor : constructors) {
                final Class<?>[] parametersClass = constructor.getParameterTypes();
                if (parametersClass.length == arguments.length) {
                    boolean exact = true;
                    for (int i = 0; i < arguments.length; i++) {
                        if (arguments[i] != null && !parametersClass[i].isAssignableFrom(arguments[i].getClass())) {
                            exact = false;
                            break;
                        }
                    }
                    if (exact) {
                        classType = (T) constructor.newInstance(arguments);
                        break;
                    }
                }
            }
        }
        return classType;
    }

    /**
     * @param verificatorClassName
     * @param name
     * @param params
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static Verificator loadVerificatorClass(final String verificatorClassName, final String name,
            final Map<String, String> params) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Verificator verificator = null;
        if (verificatorClassName != null && verificatorClassName.trim().length() > 0) {
            verificator = loadClass(verificatorClassName, name, params);
            if (verificator == null) {
                verificator = loadClass(verificatorClassName, params);
            }
            if (verificator == null) {
                verificator = loadClass(verificatorClassName, name);
            }
            if (verificator == null) {
                verificator = loadClass(verificatorClassName);
            }
        }
        return verificator;
    }

    /**
     * @param context
     */
    public static void closeQuietly(final Context context) {
        if (context != null) {
            try {
                context.close();
            } catch (final NamingException e) {
            }
        }
    }

    /**
     * @param closeable
     */
    public static void closeQuietly(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (final IOException e) {
            }
        }
    }

    /**
     * @param resultSet
     */
    public static void closeQuietly(final ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (final SQLException e) {
            }
        }
    }

    /**
     * @param statement
     */
    public static void closeQuietly(final Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (final SQLException e) {
            }
        }
    }

    /**
     * @param connection
     */
    public static void closeQuietly(final Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (final SQLException e) {
            }
        }
    }

    /**
     * @param socket
     */
    public static void closeQuietly(final Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (final IOException e) {
            }
        }
    }
}
