package es.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.executors.impl.OpenOfficeExecutor;

public class OpenOfficeExecutorTest {

    private OpenOfficeExecutor executor;

    @Test
    public void testExecutorTestMeParamsNull() {
        executor = new OpenOfficeExecutor(null);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        executor = new OpenOfficeExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsHostPortNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "notfound");
        params.put("port", "21");
        executor = new OpenOfficeExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "localhost");
        params.put("port", "8700");
        executor = new OpenOfficeExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

}
