package es.jtestme.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

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
}
