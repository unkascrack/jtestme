package es.map.jtestme.executors.impl;

import java.lang.reflect.Constructor;
import java.util.Map;

import es.map.jtestme.domain.JTestMeResult;
import es.map.jtestme.executors.JTestMeExecutor;

public class CustomExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_CLASS = "class";

    public CustomExecutor(final Map<String, String> params) {
        super(params);
    }

    @SuppressWarnings("rawtypes")
    public JTestMeResult executor() {
        final JTestMeResult result = super.getResult();

        final String className = params.get(PARAM_CLASS);

        try {
            JTestMeExecutor executor = null;
            final Class clase = Class.forName(className);
            final Constructor[] constructors = clase.getConstructors();
            if (constructors != null && constructors.length == 0) {
                for (final Constructor constructor : constructors) {
                    if (constructor.isAccessible()) {
                        final Class[] parametersClass = constructor.getParameterTypes();
                        if (parametersClass == null || parametersClass.length == 0) {
                            executor = (JTestMeExecutor) clase.newInstance();
                            break;
                        } else if (parametersClass.length == 1 && parametersClass[0].equals(Map.class)) {
                            executor = (JTestMeExecutor) constructor.newInstance(params);
                            break;
                        }
                    }
                }
            }
            if (executor != null) {
                final JTestMeResult resultExecutor = executor.executor();
                result.setSuscess(resultExecutor.isSuscess());
                result.setMessage(resultExecutor.getMessage());
            } else {
                result.setMessage("No se ha podido cargar el custom executor: " + className);
            }
        } catch (final ClassNotFoundException e) {
            result.setMessage(e.getMessage());
        } catch (final InstantiationException e) {
            result.setMessage(e.getMessage());
        } catch (final Throwable e) {
            result.setMessage(e.getMessage());
        }
        return result;
    }

}
