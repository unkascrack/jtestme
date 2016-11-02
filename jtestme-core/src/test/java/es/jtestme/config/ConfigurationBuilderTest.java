package es.jtestme.config;

import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class ConfigurationBuilderTest {

    private final ConfigurationBuilder configuration = ConfigurationBuilder.getInstance();

    @Test
    public void testJTestMeConfigurationNotNull() {
        Assert.assertNotNull(this.configuration);
    }

    @Test
    public void testGetConfigLocationNull() {
        final String configLocation = this.configuration.getConfigLocation(null);
        Assert.assertTrue(configLocation.endsWith("jtestme.properties"));
    }

    @Test
    public void testGetConfigLocationPathNotFound() {
        final String configLocation = this.configuration.getConfigLocation("./jtestme-noexiste.properties");
        Assert.assertTrue(configLocation.endsWith("jtestme.properties"));
    }

    @Test
    public void testGetConfigLocationPathFound() {
        final String configLocation = this.configuration
                .getConfigLocation(getClass().getClassLoader().getResource("jtestme.properties").getFile());
        Assert.assertTrue(configLocation.endsWith("jtestme.properties"));
    }

    @Test
    public void testGetConfigLocationClasspathNotFound() {
        final String configLocation = this.configuration.getConfigLocation("classpath:jtestme-noexiste.properties");
        Assert.assertTrue(configLocation.endsWith("jtestme.properties"));
    }

    @Test
    public void testGetConfigLocationClasspathFound() {
        final String configLocation = this.configuration.getConfigLocation("classpath:jtestme.properties");
        Assert.assertTrue(configLocation.endsWith("jtestme.properties"));
    }

    @Test
    public void testExistsConfigLocationNull() {
        final boolean existsConfigLocation = this.configuration.existsConfigLocation(null);
        Assert.assertFalse(existsConfigLocation);
    }

    @Test
    public void testExistsConfigLocationPathNotFound() {

        final boolean existsConfigLocation = this.configuration.existsConfigLocation("./jtestme-noexiste.properties");
        Assert.assertFalse(existsConfigLocation);
    }

    @Test
    public void testExistsConfigLocationPathFound() {
        final boolean existsConfigLocation = this.configuration
                .existsConfigLocation(getClass().getClassLoader().getResource("jtestme.properties").getFile());
        Assert.assertTrue(existsConfigLocation);
    }

    @Test
    public void testExistsConfigLocationClasspathNotFound() {
        final boolean existsConfigLocation =
                this.configuration.existsConfigLocation("classpath:jtestme-noexiste.properties");
        Assert.assertFalse(existsConfigLocation);
    }

    @Test
    public void testExistsConfigLocationClasspathFound() {
        final boolean existsConfigLocation = this.configuration.existsConfigLocation("classpath:jtestme.properties");
        Assert.assertTrue(existsConfigLocation);
    }

    @Test
    public void testLoadPropertiesConfigLocationNull() {
        final Properties properties = this.configuration.loadProperties(null);
        Assert.assertNull(properties);
    }

    @Test
    public void testLoadPropertiesConfigLocationNotFound() {
        final Properties properties = this.configuration.loadProperties("classpath:jtestme-noexiste.properties");
        Assert.assertNull(properties);
    }

    @Test
    public void testLoadPropertiesConfigLocationFound() {
        final String configLocation = this.configuration.getConfigLocation("classpath:jtestme.properties");
        final Properties properties = this.configuration.loadProperties(configLocation);
        Assert.assertNotNull(properties);
    }

    @Test
    public void testGetNameKeyPropertyLowerCase() {
        Assert.assertEquals(this.configuration.getNameKeyProperty("jtestme.xxx.name"), "jtestme.xxx");
        Assert.assertEquals(this.configuration.getNameKeyProperty("xxx.name"), "xxx");
        Assert.assertEquals(this.configuration.getNameKeyProperty("jtestme.xxx.param.url"), "jtestme.xxx");
        Assert.assertEquals(this.configuration.getNameKeyProperty("xxx.param.url"), "xxx");
    }

    @Test
    public void testGetNameKeyPropertyUpperCase() {
        Assert.assertEquals(this.configuration.getNameKeyProperty("JTESTME.XXX.NAME"), "jtestme.xxx");
        Assert.assertEquals(this.configuration.getNameKeyProperty("XXX.NAME"), "xxx");
        Assert.assertEquals(this.configuration.getNameKeyProperty("JTESTME.XXX.PARAM.URL"), "jtestme.xxx");
        Assert.assertEquals(this.configuration.getNameKeyProperty("XXX.PARAM.URL"), "xxx");
    }

    @Test
    public void testGetParamKeyPropertyLowerCase() {
        Assert.assertEquals(this.configuration.getParamKeyProperty("jtestme.xxx.name"), "name");
        Assert.assertEquals(this.configuration.getParamKeyProperty("xxx.name"), "name");
        Assert.assertEquals(this.configuration.getParamKeyProperty("jtestme.xxx.param.url"), "url");
        Assert.assertEquals(this.configuration.getParamKeyProperty("xxx.param.url"), "url");
    }

    @Test
    public void testGetParamKeyPropertyUpperCase() {
        Assert.assertEquals(this.configuration.getParamKeyProperty("JTESTME.XXX.NAME"), "name");
        Assert.assertEquals(this.configuration.getParamKeyProperty("XXX.NAME"), "name");
        Assert.assertEquals(this.configuration.getParamKeyProperty("JTESTME.XXX.PARAM.URL"), "url");
        Assert.assertEquals(this.configuration.getParamKeyProperty("XXX.PARAM.URL"), "url");
    }

    @Test
    public void testReadProperties() {
        final String configLocation = this.configuration.getConfigLocation("classpath:jtestme.properties");
        final Properties properties = this.configuration.loadProperties(configLocation);
        final Map<String, Map<String, String>> params = this.configuration.readProperties(properties);
        Assert.assertFalse(params.isEmpty());
    }
}
