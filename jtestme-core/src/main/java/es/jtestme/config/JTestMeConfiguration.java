package es.jtestme.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        final Properties properties = loadProperties(configPath);
        if (properties != null) {
            params = readProperties(properties);
        }
        return params;
    }

    /**
     * @param configLocation
     * @return
     */
    final Properties loadProperties(final String configLocation) {
        JTestMeLogger.info("JavaTestMe loading configuration: " + configLocation);

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
        } catch (final FileNotFoundException e) {
            JTestMeLogger.warn("JTestMe could not load configuration from: " + configLocation);
        } catch (final IOException e) {
            JTestMeLogger.warn("JavaTestMe configuration " + e.toString());
        } catch (final Exception e) {
            JTestMeLogger.warn("JavaTestMe configuration " + e.toString());
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
        String configPath = configLocation != null && configLocation.trim().length() > 0 ? configLocation
                : DEFAULT_CONFIG_LOCATION;
        if (configPath.toLowerCase().startsWith("classpath:")) {
            final String auxPath = configPath.substring(configPath.toLowerCase().indexOf(":") + 1);
            final URL url = JTestMeConfiguration.class.getClassLoader().getResource(auxPath);
            configPath = url != null ? url.getFile() : null;
        } else {
            final File file = new File(configPath);
            configPath = file.exists() ? configPath : null;
        }
        return configPath;
    }
}
