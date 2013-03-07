package es.map.jtestme.config;

import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

public class JTestMeConfigurationTest {

    private final JTestMeConfiguration configuration = JTestMeConfiguration.getInstance();

    @Test
    public void testJTestMeConfigurationNotNull() {
        Assert.assertNotNull(configuration);
    }

    @Test
    public void testGetConfigLocationNull() {
        final String configLocation = configuration.getConfigLocation(null);
        Assert.assertTrue(configLocation.endsWith("jtestme.properties"));
    }

    @Test
    public void testGetConfigLocationPathNotFound() {
        final String configLocation = configuration.getConfigLocation("./jtestme-noexiste.properties");
        Assert.assertNull(configLocation);
    }

    @Test
    public void testGetConfigLocationClasspathFound() {
        final String configLocation = configuration.getConfigLocation("classpath:jtestme.properties");
        Assert.assertTrue(configLocation.endsWith("jtestme.properties"));
    }

    @Test
    public void testGetConfigLocationClasspathNotFound() {
        final String configLocation = configuration.getConfigLocation("classpath:jtestme-noexiste.properties");
        Assert.assertNull(configLocation);
    }

    @Test
    public void testLoadPropertiesConfigLocationNull() {
        final Properties properties = configuration.loadProperties(null);
        Assert.assertNull(properties);
    }

    @Test
    public void testLoadPropertiesConfigLocationNotFound() {
        final Properties properties = configuration.loadProperties("classpath:jtestme-noexiste.properties");
        Assert.assertNull(properties);
    }

    @Test
    public void testLoadPropertiesConfigLocationFound() {
        final String configLocation = configuration.getConfigLocation("classpath:jtestme.properties");
        final Properties properties = configuration.loadProperties(configLocation);
        Assert.assertNotNull(properties);
    }

    @Test
    public void testGetNameKeyPropertyLowerCase() {
        Assert.assertEquals(configuration.getNameKeyProperty("jtestme.xxx.name"), "jtestme.xxx");
        Assert.assertEquals(configuration.getNameKeyProperty("xxx.name"), "xxx");
        Assert.assertEquals(configuration.getNameKeyProperty("jtestme.xxx.param.url"), "jtestme.xxx");
        Assert.assertEquals(configuration.getNameKeyProperty("xxx.param.url"), "xxx");
    }

    @Test
    public void testGetNameKeyPropertyUpperCase() {
        Assert.assertEquals(configuration.getNameKeyProperty("JTESTME.XXX.NAME"), "jtestme.xxx");
        Assert.assertEquals(configuration.getNameKeyProperty("XXX.NAME"), "xxx");
        Assert.assertEquals(configuration.getNameKeyProperty("JTESTME.XXX.PARAM.URL"), "jtestme.xxx");
        Assert.assertEquals(configuration.getNameKeyProperty("XXX.PARAM.URL"), "xxx");
    }

    @Test
    public void testGetParamKeyPropertyLowerCase() {
        Assert.assertEquals(configuration.getParamKeyProperty("jtestme.xxx.name"), "name");
        Assert.assertEquals(configuration.getParamKeyProperty("xxx.name"), "name");
        Assert.assertEquals(configuration.getParamKeyProperty("jtestme.xxx.param.url"), "url");
        Assert.assertEquals(configuration.getParamKeyProperty("xxx.param.url"), "url");
    }

    @Test
    public void testGetParamKeyPropertyUpperCase() {
        Assert.assertEquals(configuration.getParamKeyProperty("JTESTME.XXX.NAME"), "name");
        Assert.assertEquals(configuration.getParamKeyProperty("XXX.NAME"), "name");
        Assert.assertEquals(configuration.getParamKeyProperty("JTESTME.XXX.PARAM.URL"), "url");
        Assert.assertEquals(configuration.getParamKeyProperty("XXX.PARAM.URL"), "url");
    }

    @Test
    public void testReadProperties() {
        final String configLocation = configuration.getConfigLocation("classpath:jtestme.properties");
        final Properties properties = configuration.loadProperties(configLocation);
        final Map<String, Map<String, String>> params = configuration.readProperties(properties);
        Assert.assertFalse(params.isEmpty());
    }
}
