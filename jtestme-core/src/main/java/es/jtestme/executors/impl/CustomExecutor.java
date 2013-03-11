package es.jtestme.executors.impl;

import java.lang.reflect.Constructor;
import java.util.Map;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.executors.JTestMeExecutor;

public class CustomExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_CLASS = "class";

    private final String className;

    public CustomExecutor(final Map<String, String> params) {
        super(params);
        className = getParamString(PARAM_CLASS);
    }

    @SuppressWarnings("rawtypes")
    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        try {
            JTestMeExecutor executor = null;
            final Class clase = Class.forName(className);
            final Constructor[] constructors = clase.getConstructors();
            if (constructors != null && constructors.length > 0) {
                for (final Constructor constructor : constructors) {
                    final Class[] parametersClass = constructor.getParameterTypes();
                    if (parametersClass == null || parametersClass.length == 0) {
                        executor = (JTestMeExecutor) clase.newInstance();
                        break;
                    } else if (parametersClass.length == 1 && parametersClass[0].equals(Map.class)) {
                        executor = (JTestMeExecutor) constructor.newInstance(getParams());
                        break;
                    } else if (parametersClass.length == 2 && parametersClass[0].equals(String.class)
                            && parametersClass[1].equals(Map.class)) {
                        executor = (JTestMeExecutor) constructor.newInstance(getName(), getParams());
                        break;
                    }
                }
            }
            if (executor != null) {
                final JTestMeResult resultExecutor = executor.executeTestMe();
                result.setSuscess(resultExecutor.isSuscess());
                result.setMessage(resultExecutor.getMessage());
            } else {
                result.setMessage("No se ha podido cargar el custom executor: " + className);
            }
        } catch (final ClassNotFoundException e) {
            result.setCause(e);
        } catch (final InstantiationException e) {
            result.setCause(e);
        } catch (final Throwable e) {
            result.setCause(e);
        }
        return result;
    }

}
