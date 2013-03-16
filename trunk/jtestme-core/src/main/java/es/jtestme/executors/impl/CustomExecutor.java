package es.jtestme.executors.impl;

import java.util.Map;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.executors.JTestMeExecutor;
import es.jtestme.logger.JTestMeLogger;
import es.jtestme.utils.JTestMeUtils;

public class CustomExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_CLASS = "class";

    private final JTestMeExecutor customExecutor;

    public CustomExecutor(final Map<String, String> params) {
        super(params);
        final String className = getParamString(PARAM_CLASS);
        removeParam(PARAM_CLASS);
        customExecutor = loadCustomExecutor(className);
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();
        if (customExecutor == null) {
            result.setMessage("No se ha cargado correctamente el custom executor: " + getName());
        } else {
            try {
                final JTestMeResult resultCustomExecutor = customExecutor.executeTestMe();
                result.setSuscess(resultCustomExecutor.isSuscess());
                result.setMessage(resultCustomExecutor.getMessage());
                result.setCause(resultCustomExecutor.getCause());
            } catch (final Throwable e) {
                result.setCause(e);
            }
        }
        return result;
    }

    private JTestMeExecutor loadCustomExecutor(final String className) {
        JTestMeExecutor executor = null;
        try {
            executor = JTestMeUtils.loadExecutor(className, getName(), getParams());
        } catch (final Throwable e) {
            JTestMeLogger.warn("No se ha podido cargar el custom executor: " + className, e);
        }
        return executor;
    }
}
