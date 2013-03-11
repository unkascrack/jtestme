package es.jtestme.executors.impl;

import java.util.Map;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.executors.JTestMeExecutor;

public abstract class JTestMeDefaultExecutor implements JTestMeExecutor {

    public static final String PARAM_TYPE = "type";

    private static final String PARAM_NAME = "name";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_RESOLUTION = "resolution";
    private static final String PARAM_OPTIONAL = "optional";

    private final String name;
    private final Map<String, String> params;

    /**
     * @param params
     */
    public JTestMeDefaultExecutor(final Map<String, String> params) {
        this(params != null ? params.get(PARAM_NAME) : null, params);
    }

    /**
     * @param name
     * @param params
     */
    public JTestMeDefaultExecutor(final String name, final Map<String, String> params) {
        this.params = params;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getParams() {
        return params;
    }

    /**
     * @return
     */
    protected JTestMeResult getResult() {
        final JTestMeResult result = new JTestMeResult();
        if (params != null) {
            result.setType(params.get(PARAM_TYPE));
            result.setName(params.get(PARAM_NAME));
            result.setDescription(params.get(PARAM_DESCRIPTION));
            result.setResolution(params.get(PARAM_RESOLUTION));
            result.setOptional(getParamBoolean(PARAM_OPTIONAL, false));
            result.setParameters(params);
        }
        return result;
    }

    /**
     * @param param
     * @return
     */
    protected String getParamString(final String param) {
        return getParamString(param, null);
    }

    /**
     * @param param
     * @param defaultValue
     * @return
     */
    protected String getParamString(final String param, final String defaultValue) {
        final String value = param != null && param.trim().length() > 0 && params != null ? params.get(param) : null;
        return value != null && value.trim().length() > 0 ? value : defaultValue;
    }

    /**
     * @param param
     * @param defaultValue
     * @return
     */
    protected boolean getParamBoolean(final String param, final boolean defaultValue) {
        final String value = getParamString(param);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    /**
     * @param param
     * @param defaultValue
     * @return
     */
    protected Integer getParamInteger(final String param, final Integer defaultValue) {
        Integer val = null;
        try {
            final String value = getParamString(param);
            val = value != null ? Integer.valueOf(value) : null;
        } catch (final NumberFormatException e) {
            val = null;
        }
        return val != null ? val : defaultValue;
    }
}
