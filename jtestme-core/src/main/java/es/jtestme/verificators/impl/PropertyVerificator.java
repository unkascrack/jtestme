package es.jtestme.verificators.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.viewers.Viewer;

public final class PropertyVerificator extends AbstractVerificator {

    private static final String PARAM_VARIABLES = "variables";

    private final String[] variables;

    public PropertyVerificator(final Map<String, String> params) {
        super(params);
        this.variables = convertVariables(getParamString(PARAM_VARIABLES));
    }

    public VerificatorResult execute() {
        final VerificatorResult result = new VerificatorResult();
        if (this.variables.length == 0) {
            result.setMessage(getClass().getSimpleName() + ": no se ha definido las variables para verificar.");
        }

        if (this.variables.length > 0) {
            final StringBuilder builder = new StringBuilder();
            for (final String variable : this.variables) {
                if (System.getProperty(variable) == null) {
                    builder.append(String.format(" * Variable '%s' no está definida", variable));
                    builder.append(Viewer.NEW_LINE);
                }
            }
            if (builder.length() > 0) {
                result.setMessage(getClass().getSimpleName() + ": error." + Viewer.NEW_LINE + builder.toString());
            } else {
                result.setSuccess(true);
            }
        }
        return result;
    }

    private String[] convertVariables(final String variablesValue) {
        final List<String> listVariables = new ArrayList<String>();
        if (variablesValue != null) {
            for (final String variable : variablesValue.split(",")) {
                if (variable != null && variable.trim().length() > 0) {
                    listVariables.add(variable.trim());
                }
            }
        }
        return listVariables.toArray(new String[listVariables.size()]);
    }
}
