package es.jtestme.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import es.jtestme.executors.JTestMeExecutor;

public final class JTestMeUtils {

    private JTestMeUtils() {
    }

    /**
     * @param executorClassName
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
    public static final JTestMeExecutor loadExecutor(final String executorClassName, final String name,
            final Map<String, String> params) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JTestMeExecutor executor = null;
        if (executorClassName != null && executorClassName.trim().length() > 0) {
            final Class<? extends JTestMeExecutor> clase = (Class<? extends JTestMeExecutor>) Class
                    .forName(executorClassName);
            final Constructor<?>[] constructors = clase.getConstructors();
            for (final Constructor<?> constructor : constructors) {
                final Class<?>[] parametersClass = constructor.getParameterTypes();
                if (parametersClass == null || parametersClass.length == 0) {
                    executor = clase.newInstance();
                    break;
                } else if (parametersClass.length == 1 && parametersClass[0].equals(Map.class)) {
                    executor = (JTestMeExecutor) constructor.newInstance(params);
                    break;
                } else if (parametersClass.length == 2 && parametersClass[0].equals(String.class)
                        && parametersClass[1].equals(Map.class)) {
                    executor = (JTestMeExecutor) constructor.newInstance(name, params);
                    break;
                }
            }
        }
        return executor;
    }
}
