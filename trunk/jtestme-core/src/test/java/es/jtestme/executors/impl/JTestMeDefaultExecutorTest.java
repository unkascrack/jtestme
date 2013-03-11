package es.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.executors.impl.JTestMeDefaultExecutor;

public class JTestMeDefaultExecutorTest {

    private JTestMeDefaultExecutor executor;

    @Test
    public void testGetParamStringParamsNullValueNull() {
        executor = new FakeExecutor(null);
        final String value = executor.getParamString("name");
        Assert.assertNull(value);
    }

    @Test
    public void testGetParamStringParamsEmptyValueNull() {
        final Map<String, String> params = new HashMap<String, String>();
        executor = new FakeExecutor(params);
        final String value = executor.getParamString("name");
        Assert.assertNull(value);
    }

    @Test
    public void testGetParamStringWithParamNotFoundValueNull() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        executor = new FakeExecutor(params);
        final String value = executor.getParamString("notfound");
        Assert.assertNull(value);
    }

    @Test
    public void testGetParamStringParamsFoundValueNotNull() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        executor = new FakeExecutor(params);
        final String value = executor.getParamString("name");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "name");
    }

    @Test
    public void testGetParamStringParamsNullValueIsDefault() {
        executor = new FakeExecutor(null);
        final String value = executor.getParamString("name", "default");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "default");
    }

    @Test
    public void testGetParamStringParamsEmptyValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        executor = new FakeExecutor(params);
        final String value = executor.getParamString("name", "default");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "default");
    }

    @Test
    public void testGetParamStringWithParamNotFoundValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        executor = new FakeExecutor(params);
        final String value = executor.getParamString("notfound", "default");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "default");
    }

    @Test
    public void testGetParamStringParamsFoundValueIsNotDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        executor = new FakeExecutor(params);
        final String value = executor.getParamString("name", "default");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "name");
    }

    @Test
    public void testGetParamIntegerParamsNullValueIsDefault() {
        executor = new FakeExecutor(null);
        final Integer value = executor.getParamInteger("name", 0);
        Assert.assertNotNull(value);
        Assert.assertSame(value, 0);
    }

    @Test
    public void testGetParamIntegerParamsEmptyValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        executor = new FakeExecutor(params);
        final Integer value = executor.getParamInteger("name", 0);
        Assert.assertNotNull(value);
        Assert.assertSame(value, 0);
    }

    @Test
    public void testGetParamIntegerWithParamNotFoundValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "10");
        executor = new FakeExecutor(params);
        final Integer value = executor.getParamInteger("notfound", 0);
        Assert.assertNotNull(value);
        Assert.assertSame(value, 0);
    }

    @Test
    public void testGetParamIntegerParamsFoundValueIsNotDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "10");
        executor = new FakeExecutor(params);
        final Integer value = executor.getParamInteger("name", 0);
        Assert.assertNotNull(value);
        Assert.assertSame(value, 10);
    }

    @Test
    public void testGetParamBooleanParamsNullValueIsDefault() {
        executor = new FakeExecutor(null);
        final boolean value = executor.getParamBoolean("name", false);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, false);
    }

    @Test
    public void testGetParamBooleanParamsEmptyValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        executor = new FakeExecutor(params);
        final boolean value = executor.getParamBoolean("name", false);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, false);
    }

    @Test
    public void testGetParamBooleanWithParamNotFoundValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "true");
        executor = new FakeExecutor(params);
        final boolean value = executor.getParamBoolean("notfound", false);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, false);
    }

    @Test
    public void testGetParamBooleanParamsFoundValueIsNotDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "true");
        executor = new FakeExecutor(params);
        final boolean value = executor.getParamBoolean("name", false);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, true);
    }

    @Test
    public void testGetParamBooleanParamsBooleanIncorrectValueIsDefault() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "yes");
        executor = new FakeExecutor(params);
        final boolean value = executor.getParamBoolean("name", false);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, false);
    }

    @Test
    public void testGetResultParamsNull() {
        executor = new FakeExecutor(null);
        final JTestMeResult result = executor.getResult();
        Assert.assertNotNull(result);
        Assert.assertNull(result.getName());
    }

    @Test
    public void testGetResultParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        executor = new FakeExecutor(params);
        final JTestMeResult result = executor.getResult();
        Assert.assertNotNull(result);
        Assert.assertNull(result.getName());
    }

    @Test
    public void testGetResultParamsNameNotSet() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("notfound", "notfound");
        executor = new FakeExecutor(params);
        final JTestMeResult result = executor.getResult();
        Assert.assertNotNull(result);
        Assert.assertNull(result.getName());
    }

    @Test
    public void testGetResultParamsNameSet() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        executor = new FakeExecutor(params);
        final JTestMeResult result = executor.getResult();
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getName());
    }

    private class FakeExecutor extends JTestMeDefaultExecutor {

        public FakeExecutor(final Map<String, String> params) {
            super(params);
        }

        public FakeExecutor(final String name, final Map<String, String> params) {
            super(name, params);
        }

        public JTestMeResult executeTestMe() {
            return null;
        }

    }
}
