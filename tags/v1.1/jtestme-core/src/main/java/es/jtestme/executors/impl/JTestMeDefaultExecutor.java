package es.jtestme.executors.impl;

import java.net.URL;
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
    private String defaultTrustStore;
    private String defaultTrustStorePassword;

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
     * @param trustStore
     * @param trustStorePassword
     */
    protected void loadTrustStore(final String trustStore, final String trustStorePassword) {
        if (trustStore != null && trustStore.length() > 0) {
            defaultTrustStore = System.getProperty("javax.net.ssl.trustStore");
            System.setProperty("javax.net.ssl.trustStore", trustStore);
        }

        if (trustStorePassword != null && trustStorePassword.length() > 0) {
            defaultTrustStorePassword = System.getProperty("javax.net.ssl.trustStorePassword");
            System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
        }
    }

    /**
     * @param trustStore
     * @param trustStorePassword
     */
    protected void relaseTrustStore(final String trustStore, final String trustStorePassword) {
        if (trustStore != null && trustStore.length() > 0) {
            if (defaultTrustStore != null && defaultTrustStore.length() > 0) {
                System.setProperty("javax.net.ssl.trustStore", defaultTrustStore);
            } else {
                System.clearProperty("javax.net.ssl.trustStore");
            }
        }

        if (trustStorePassword != null && trustStorePassword.length() > 0) {
            if (defaultTrustStorePassword != null && defaultTrustStorePassword.length() > 0) {
                System.setProperty("javax.net.ssl.trustStorePassword", defaultTrustStorePassword);
            } else {
                System.clearProperty("javax.net.ssl.trustStorePassword");
            }
        }
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

    /**
     * @param param
     * @param defaultValue
     * @return
     */
    protected String getParamString(final String param, final String defaultValue) {
        String value = param != null && param.trim().length() > 0 && params != null ? params.get(param) : null;
        while (containsSystemProperty(value)) {
            value = getSystemPropertyValue(value);
        }
        if (containsClasspathResource(value)) {
            value = getClasspathResource(value);
        }
        return value != null && value.trim().length() > 0 ? value.trim() : defaultValue;
    }

    /**
     * @param str
     * @return
     */
    private boolean containsSystemProperty(final String str) {
        return str != null && str.contains("${") && str.substring(str.indexOf("${")).contains("}");
    }

    /**
     * @param str
     * @return
     */
    private String getSystemPropertyValue(final String str) {
        String value = str;
        if (value != null) {
            final String part1 = value.substring(0, value.indexOf("${"));
            final String part2 = value.substring(value.indexOf("}") + 1);
            final String keyProperty = value.substring(value.indexOf("${") + 2, value.indexOf("}"));
            value = System.getProperty(keyProperty);
            if (part1.length() > 0) {
                value = part1 + value;
            }
            if (part2.length() > 0) {
                value = value + part2;
            }
        }
        return value;
    }

    /**
     * @param str
     * @return
     */
    private boolean containsClasspathResource(final String str) {
        return str != null && str.toLowerCase().startsWith("classpath:");
    }

    /**
     * @param str
     * @return
     */
    private String getClasspathResource(final String str) {
        String value = str;
        if (value != null) {
            final String resource = value.substring(value.toLowerCase().indexOf("classpath:") + 10);
            final URL url = getClass().getClassLoader().getResource(resource);
            value = url != null ? url.getFile() : null;
        }
        return value;
    }
}
