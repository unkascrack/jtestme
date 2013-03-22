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
    @SuppressWarnings("unchecked")
    public static final Verificator loadVerificator(final String verificatorClassName, final String name,
            final Map<String, String> params) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Verificator verificator = null;
        if (verificatorClassName != null && verificatorClassName.trim().length() > 0) {
            final Class<? extends Verificator> clase = (Class<? extends Verificator>) Class
                    .forName(verificatorClassName);
            final Constructor<?>[] constructors = clase.getConstructors();
            for (final Constructor<?> constructor : constructors) {
                final Class<?>[] parametersClass = constructor.getParameterTypes();
                if (parametersClass == null || parametersClass.length == 0) {
                    verificator = clase.newInstance();
                    break;
                } else if (parametersClass.length == 1 && parametersClass[0].equals(Map.class)) {
                    verificator = (Verificator) constructor.newInstance(params);
                    break;
                } else if (parametersClass.length == 2 && parametersClass[0].equals(String.class)
                        && parametersClass[1].equals(Map.class)) {
                    verificator = (Verificator) constructor.newInstance(name, params);
                    break;
                }
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
