package es.jtestme.verificators.impl;

import java.util.Map;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.logger.JTestMeLogger;
import es.jtestme.utils.JTestMeUtils;
import es.jtestme.verificators.Verificator;

public class CustomVerificator extends AbstractVerificator {

    private static final String PARAM_CLASS = "class";

    private final Verificator customVerificator;

    public CustomVerificator(final Map<String, String> params) {
        super(params);
        final String className = getParamString(PARAM_CLASS);
        removeParam(PARAM_CLASS);
        customVerificator = loadCustomVerificator(className);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();
        if (customVerificator == null) {
            result.setMessage("No se ha cargado correctamente el custom verificator: " + getUid());
        } else {
            try {
                final VerificatorResult resultCustomVerificator = customVerificator.execute();
                result.setSuccess(resultCustomVerificator.isSuccess());
                result.setMessage(resultCustomVerificator.getMessage());
                result.setCause(resultCustomVerificator.getCause());
            } catch (final Throwable e) {
                result.setCause(e);
            }
        }
        return result;
    }

    private Verificator loadCustomVerificator(final String className) {
        Verificator verificator = null;
        try {
            verificator = JTestMeUtils.loadVerificatorClass(className, getUid(), getParams());
        } catch (final Throwable e) {
            JTestMeLogger.warn("No se ha podido cargar el custom verificator: " + className, e);
        }
        return verificator;
    }
}
