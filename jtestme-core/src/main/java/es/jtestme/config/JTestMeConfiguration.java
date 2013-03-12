package es.jtestme.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import es.jtestme.logger.JTestMeLogger;

public final class JTestMeConfiguration {

    private static final String DEFAULT_CONFIG_LOCATION = "classpath:jtestme.properties";

    private static final JTestMeConfiguration INSTANCE = new JTestMeConfiguration();

    private JTestMeConfiguration() {
    }

    public static final JTestMeConfiguration getInstance() {
        return INSTANCE;
    }

    public Map<String, Map<String, String>> loadConfiguration(final String configLocation) {
        Map<String, Map<String, String>> params = null;
        final String configPath = getConfigLocation(configLocation);
        if (configPath != null) {
            final Properties properties = loadProperties(configPath);
            if (properties != null) {
                params = readProperties(properties);
            }
        }
        return params;
    }

    /**
     * @param configLocation
     * @return
     */
    final Properties loadProperties(final String configLocation) {
        Properties properties = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configLocation);
            properties = new Properties();
            if (configLocation.toLowerCase().endsWith(".xml")) {
                properties.loadFromXML(inputStream);
            } else {
                properties.load(inputStream);
            }
            JTestMeLogger.info("JavaTestMe loading configuration: " + configLocation);
        } catch (final Throwable e) {
            JTestMeLogger.warn("JavaTestMe error loading configuration: " + e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException e) {
                }
            }
        }
        return properties;
    }

    final Map<String, Map<String, String>> readProperties(final Properties properties) {
        final Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
        for (final Entry<Object, Object> entry : properties.entrySet()) {
            JTestMeLogger.debug("JTestMe configuration property: " + entry.getKey() + "=" + entry.getValue());
            if (entry.getKey() != null) {
                final String key = entry.getKey().toString();
                final String name = getNameKeyProperty(key);
                final String param = getParamKeyProperty(key);
                final String value = entry.getValue() != null ? entry.getValue().toString() : null;
                if (!params.containsKey(name)) {
                    params.put(name, new HashMap<String, String>());
                }
                params.get(name).put(param, value);
            }
        }
        return params;
    }

    final String getNameKeyProperty(final String key) {
        String name;
        if (key.toLowerCase().contains(".param.")) {
            name = key.substring(0, key.toLowerCase().lastIndexOf(".param."));
        } else {
            name = key.substring(0, key.lastIndexOf("."));
        }
        return name.toLowerCase();
    }

    final String getParamKeyProperty(final String key) {
        return key.toLowerCase().substring(key.lastIndexOf(".") + 1);
    }

    /**
     * @param configLocation
     * @return
     */
    final String getConfigLocation(final String configLocation) {
        String path = null;
        if (existsConfigLocation(configLocation)) {
            path = convertConfigLocationToFilePath(configLocation);
        } else if (existsConfigLocation(DEFAULT_CONFIG_LOCATION)) {
            path = convertConfigLocationToFilePath(DEFAULT_CONFIG_LOCATION);
        }
        return path;
    }

    /**
     * @param configLocation
     * @return
     */
    final boolean existsConfigLocation(final String configLocation) {
        boolean exists = false;
        if (configLocation != null && configLocation.trim().length() > 0) {
            exists = convertConfigLocationToFilePath(configLocation) != null;
            if (!exists) {
                JTestMeLogger.warn("JavaTestMe could not load configuration from: " + configLocation);
            }
        }
        return exists;
    }

    /**
     * @param configLocation
     * @return
     */
    final String convertConfigLocationToFilePath(final String configLocation) {
        String filePath = null;
        if (configLocation != null && configLocation.trim().length() > 0) {
            if (configLocation.toLowerCase().startsWith("classpath:")) {
                final String auxPath = configLocation.substring(configLocation.toLowerCase().indexOf(":") + 1);
                final URL url = JTestMeConfiguration.class.getClassLoader().getResource(auxPath);
                filePath = url != null ? url.getFile() : null;
            } else {
                final File file = new File(configLocation);
                filePath = file.exists() ? file.getPath() : null;
            }
        }
        return filePath;
    }
}
