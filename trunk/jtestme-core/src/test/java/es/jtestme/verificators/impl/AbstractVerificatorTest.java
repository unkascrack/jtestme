package es.jtestme.verificators.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.jtestme.domain.VerificatorResult;

public class AbstractVerificatorTest {

    private AbstractVerificator verificator;

    @BeforeClass
    public static void setUp() {
        System.setProperty("my-system-property", "my-value-property");
    }

    @AfterClass
    public static void tearDown() {
        System.clearProperty("my-system-property");
    }

    @Test
    public void testGetParamStringParamsNullValueNull() {
        verificator = new FakeVerificator(null);
        final String value = verificator.getParamString("name");
        Assert.assertNull(value);
    }

    @Test
    public void testGetParamStringParamsEmptyValueNull() {
        final Map<String, String> params = new HashMap<String, String>();
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNull(value);
    }

    @Test
    public void testGetParamStringWithParamNotFoundValueNull() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("notfound");
        Assert.assertNull(value);
    }

    @Test
    public void testGetParamStringParamsFoundValueNotNull() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "name");
    }

    @Test
    public void testGetParamStringParamsSystemPropertyNotFoundValueNull1() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "${my-system-property-notfound}");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNull(value);
    }

    @Test
    public void testGetParamStringParamsSystemPropertyNotFoundValueNull2() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "${my-system-property-notfound}/value/");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "null/value/");
    }

    @Test
    public void testGetParamStringParamsSystemPropertyNotFoundValueNull3() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "/value/${my-system-property-notfound}");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "/value/null");
    }

    @Test
    public void testGetParamStringParamsSystemPropertyNotFoundValueNull4() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "/value/${my-system-property-notfound}/value/");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "/value/null/value/");
    }

    @Test
    public void testGetParamStringParamsSystemPropertyFoundValueNotNull1() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "${my-system-property}");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "my-value-property");
    }

    @Test
    public void testGetParamStringParamsSystemPropertyFoundValueNotNull2() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "${my-system-property}/value/");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "my-value-property/value/");
    }

    @Test
    public void testGetParamStringParamsSystemPropertyFoundValueNotNull3() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "/value/${my-system-property}");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "/value/my-value-property");
    }

    @Test
    public void testGetParamStringParamsSystemPropertyFoundValueNotNull4() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "/value/${my-system-property}/value/");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "/value/my-value-property/value/");
    }

    @Test
    public void testGetParamStringParamsClasspathResourceNoFoundValueNull() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "classpath:notfound.properties");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNull(value);
    }

    @Test
    public void testGetParamStringParamsNotClasspathResourceValueNotNull() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "java:comp/env/jdbc/datasource");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNotNull(value);
        Assert.assertEquals("java:comp/env/jdbc/datasource", value);
    }

    @Test
    public void testGetParamStringParamsClasspathResourceFoundValueNotNull() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "classpath:jtestme.properties");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name");
        Assert.assertNotNull(value);
        Assert.assertTrue(value.endsWith("jtestme.properties"));
    }

    @Test
    public void testGetParamStringParamsNullValueIsDefault() {
        verificator = new FakeVerificator(null);
        final String value = verificator.getParamString("name", "default");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "default");
    }

    @Test
    public void testGetParamStringParamsEmptyValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name", "default");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "default");
    }

    @Test
    public void testGetParamStringWithParamNotFoundValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("notfound", "default");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "default");
    }

    @Test
    public void testGetParamStringParamsFoundValueIsNotDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name", "default");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "name");
    }

    @Test
    public void testGetParamStringParamsSystemPropertyNotFoundValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "${my-system-property-notfound}");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name", "default");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "default");
    }

    @Test
    public void testGetParamStringParamsSystemPropertyFoundValueNotIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "${my-system-property}");
        verificator = new FakeVerificator(params);
        final String value = verificator.getParamString("name", "default");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "my-value-property");
    }

    @Test
    public void testGetParamIntegerParamsNullValueIsDefault() {
        verificator = new FakeVerificator(null);
        final Integer value = verificator.getParamInteger("name", 0);
        Assert.assertNotNull(value);
        Assert.assertSame(value, 0);
    }

    @Test
    public void testGetParamIntegerParamsEmptyValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        verificator = new FakeVerificator(params);
        final Integer value = verificator.getParamInteger("name", 0);
        Assert.assertNotNull(value);
        Assert.assertSame(value, 0);
    }

    @Test
    public void testGetParamIntegerWithParamNotFoundValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "10");
        verificator = new FakeVerificator(params);
        final Integer value = verificator.getParamInteger("notfound", 0);
        Assert.assertNotNull(value);
        Assert.assertSame(value, 0);
    }

    @Test
    public void testGetParamIntegerParamsFoundValueIsNotDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "10");
        verificator = new FakeVerificator(params);
        final Integer value = verificator.getParamInteger("name", 0);
        Assert.assertNotNull(value);
        Assert.assertSame(value, 10);
    }

    @Test
    public void testGetParamBooleanParamsNullValueIsDefault() {
        verificator = new FakeVerificator(null);
        final boolean value = verificator.getParamBoolean("name", false);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, false);
    }

    @Test
    public void testGetParamBooleanParamsEmptyValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        verificator = new FakeVerificator(params);
        final boolean value = verificator.getParamBoolean("name", false);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, false);
    }

    @Test
    public void testGetParamBooleanWithParamNotFoundValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "true");
        verificator = new FakeVerificator(params);
        final boolean value = verificator.getParamBoolean("notfound", false);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, false);
    }

    @Test
    public void testGetParamBooleanParamsFoundValueIsNotDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "true");
        verificator = new FakeVerificator(params);
        final boolean value = verificator.getParamBoolean("name", false);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, true);
    }

    @Test
    public void testGetParamBooleanParamsBooleanIncorrectValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "yes");
        verificator = new FakeVerificator(params);
        final boolean value = verificator.getParamBoolean("name", false);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, false);
    }

    @Test
    public void testGetResultParamsNull() {
        verificator = new FakeVerificator(null);
        final VerificatorResult result = verificator.getResult();
        Assert.assertNotNull(result);
        Assert.assertNull(result.getName());
    }

    @Test
    public void testGetResultParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        verificator = new FakeVerificator(params);
        final VerificatorResult result = verificator.getResult();
        Assert.assertNotNull(result);
        Assert.assertNull(result.getName());
    }

    @Test
    public void testGetResultParamsNameNotSet() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("notfound", "notfound");
        verificator = new FakeVerificator(params);
        final VerificatorResult result = verificator.getResult();
        Assert.assertNotNull(result);
        Assert.assertNull(result.getName());
    }

    @Test
    public void testGetResultParamsNameSet() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        verificator = new FakeVerificator(params);
        final VerificatorResult result = verificator.getResult();
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getName());
    }

    private class FakeVerificator extends AbstractVerificator {

        public FakeVerificator(final Map<String, String> params) {
            super(params);
        }

        public FakeVerificator(final String name, final Map<String, String> params) {
            super(name, params);
        }

        public VerificatorResult execute() {
            return null;
        }

    }
}
