package es.map.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.map.jtestme.domain.JTestMeResult;

public class ConnectionExecutorTest {

    private ConnectionExecutor executor;

    final static Map<String, String> params = new HashMap<String, String>();

    @Before
    public void setUp() {
        executor = new ConnectionExecutor(params);
    }

    @Test
    public void testConnectionExecutorNotNull() {
        Assert.assertNotNull(executor);
    }

    @Test
    public void testExecutorTestMeParamsEmpty() {
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
    }

    @Test
    public void testExecutorTestMeParamURLHttpNotFound() {
        params.put("url", "http://noexiste.es");
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamURLHttpOk() {
        params.put("url", "http://www.google.com");
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamURLHttpsOk() {
        params.put("url", "https://www.google.com");
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }
}
