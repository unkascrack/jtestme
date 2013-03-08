package es.map.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.map.jtestme.domain.JTestMeResult;

public class ConnectionExecutorTest {

    private ConnectionExecutor executor;

    @Test
    public void testExecutorTestMeParamsEmpty() {
        executor = new ConnectionExecutor(null);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamURLHttpNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "http://noexiste.es");
        executor = new ConnectionExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamURLHttpOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "http://appint.map.es/snc/services/ObtenerToken?wsdl");
        executor = new ConnectionExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamURLHttpsOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "https://www.google.com");
        executor = new ConnectionExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamURLHttpsCertificate() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "https://www.google.com");
        params.put("truststore", "/path/certificado");
        params.put("truststorepassword", "/path/certificado");
        executor = new ConnectionExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }
}
