package es.jtestme.verificators;

import java.util.Map;

import es.jtestme.logger.JTestMeLogger;
import es.jtestme.utils.JTestMeUtils;
import es.jtestme.verificators.impl.AbstractVerificator;

public final class VerificatorFactory {

    private final static VerificatorFactory INSTANCE = new VerificatorFactory();

    private VerificatorFactory() {
    }

    /**
     * @return
     */
    public static final VerificatorFactory getInstance() {
        return INSTANCE;
    }

    /**
     * @param name
     * @param params
     * @return
     */
    public Verificator loadVerificator(final String name, final Map<String, String> params) {
        final VerificatorType verificatorType = getVerificatorType(name, params);
        Verificator verificator = null;
        if (verificatorType != null) {
            try {
                final String verificatorClassName = verificatorType.getVerificatorClassName();
                verificator = JTestMeUtils.loadVerificatorClass(verificatorClassName, name, params);
                JTestMeLogger.debug("JTestMe loading verificator '" + name + "' of type: " + verificatorType);
            } catch (final Throwable e) {
                JTestMeLogger.warn("JTestMe could not load verificator '" + name + "' of type: " + verificatorType, e);
            }
        }
        return verificator;
    }

    /**
     * @param name
     * @param params
     * @return
     */
    private VerificatorType getVerificatorType(final String name, final Map<String, String> params) {
        VerificatorType verificatorType = null;
        if (params == null || !params.containsKey(AbstractVerificator.PARAM_TYPE)) {
            JTestMeLogger.warn("JTestMe could not load verificator '" + name + "' no typedef.");
        } else {
            final String type = params.get(AbstractVerificator.PARAM_TYPE);
            verificatorType = VerificatorType.toType(type);
            if (verificatorType == null) {
                JTestMeLogger.warn("JTestMe could not load verificator '" + name + "' of type: " + type);
            }
        }
        return verificatorType;
    }
}
