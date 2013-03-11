package es.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.executors.impl.CustomExecutor;

public class CustomExecutorTest {

    private CustomExecutor executor;

    @Test
    public void testExecutorTestMeParamsNull() {
        executor = new CustomExecutor(null);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        executor = new CustomExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsClassNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("class", "notfound");
        executor = new CustomExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsClassFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("class", FakeExecutor.class.getName());
        executor = new CustomExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsClassFoundWithParams() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("class", FakeExecutor.class.getName());
        params.put("url", "http://www.google.com");
        executor = new CustomExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }
}
