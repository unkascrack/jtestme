package es.map.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.map.jtestme.domain.JTestMeResult;

public class WebServiceExecutorTest {

    private WebServiceExecutor executor;

    @Test
    public void testExecutorTestMeParamsNull() {
        executor = new WebServiceExecutor(null);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsRPC() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rpc");
        params.put("endpoint", "http://localhost:80/MyHelloService/MyHelloService");
        params.put("namespaceuri", "MyHelloService");
        params.put("localpart", "MyHelloServiceRPC");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsSOAP() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "soap");
        params.put("endpoint", "http://localhost:80/MyHelloService/MyHelloService");
        params.put("namespaceuri", "MyHelloService");
        params.put("localpart", "MyHelloServiceRPC");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsREST() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rest");
        params.put("endpoint", "http://localhost:80/MyHelloService/MyHelloService");
        params.put("namespaceuri", "MyHelloService");
        params.put("localpart", "MyHelloServiceRPC");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }
}
