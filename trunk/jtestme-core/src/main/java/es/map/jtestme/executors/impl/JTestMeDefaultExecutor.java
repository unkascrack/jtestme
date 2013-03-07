package es.map.jtestme.executors.impl;

import java.util.Date;
import java.util.Map;

import es.map.jtestme.domain.JTestMeResult;
import es.map.jtestme.executors.JTestMeExecutor;

public abstract class JTestMeDefaultExecutor implements JTestMeExecutor {

    public static final String PARAM_TYPE = "type";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_DESCRIPTION = "description";
    public static final String PARAM_RESOLUTION = "resolution";
    public static final String PARAM_OPTIONAL = "optional";

    protected final Map<String, String> params;

    public JTestMeDefaultExecutor(final Map<String, String> params) {
        this.params = params;
    }

    public String getName() {
        return params.get(PARAM_TYPE);
    }

    protected JTestMeResult getResult() {
        final JTestMeResult result = new JTestMeResult();
        result.setType(params.get(PARAM_TYPE));
        result.setName(params.get(PARAM_NAME));
        result.setDescription(params.get(PARAM_DESCRIPTION));
        result.setResolution(params.get(PARAM_RESOLUTION));
        result.setOptional(toBoolean(params.get(PARAM_OPTIONAL), false));
        result.setParameters(params);
        result.setTime(new Date());
        return result;
    }

    protected boolean toBoolean(final String value, final boolean defaultBool) {
        return value != null ? Boolean.parseBoolean(value) : defaultBool;
    }
}
