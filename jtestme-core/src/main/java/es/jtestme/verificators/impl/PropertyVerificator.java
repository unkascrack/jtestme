package es.jtestme.verificators.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.viewers.Viewer;

public final class PropertyVerificator extends AbstractVerificator {

    private static final String PARAM_PROPERTIES = "properties";

    private final String[] properties;

    public PropertyVerificator(final Map<String, String> params) {
        super(params);
        this.properties = convertProperties(getParamString(PARAM_PROPERTIES));
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();
        if (this.properties.length == 0) {
            result.setMessage(getClass().getSimpleName() + ": no se ha definido las propiedades para verificar.");
        }

        if (this.properties.length > 0) {
            final StringBuilder builder = new StringBuilder();
            for (final String variable : this.properties) {
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

    private String[] convertProperties(final String properties) {
        final List<String> listProperties = new ArrayList<String>();
        if (properties != null) {
            for (final String variable : properties.split(",")) {
                if (variable != null && variable.trim().length() > 0) {
                    listProperties.add(variable.trim());
                }
            }
        }
        return listProperties.toArray(new String[listProperties.size()]);
    }
}
