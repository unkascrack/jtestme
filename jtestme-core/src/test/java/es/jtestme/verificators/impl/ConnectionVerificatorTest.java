package es.jtestme.verificators.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.domain.VerificatorResult;

public class ConnectionVerificatorTest {

    private static final String PROXY_HOST = "";
    private static final String PROXY_PORT = "";

    private ConnectionVerificator verificator;

    @Test
    public void testExecuteParamsEmpty() {
        this.verificator = new ConnectionVerificator(null);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamURLHttpNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "http://noexiste.es");
        this.verificator = new ConnectionVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamURLHttpOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "http://www.google.com");
        params.put("proxyhost", PROXY_HOST);
        params.put("proxyport", PROXY_PORT);
        this.verificator = new ConnectionVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testExecuteParamURLHttpsOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "https://www.google.com");
        params.put("proxyhost", PROXY_HOST);
        params.put("proxyport", PROXY_PORT);
        this.verificator = new ConnectionVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testExecuteParamURLHttpsCertificate() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "https://www.google.com");
        params.put("proxyhost", PROXY_HOST);
        params.put("proxyport", PROXY_PORT);
        params.put("truststore", "/path/certificado");
        params.put("truststorepassword", "/path/certificado");
        this.verificator = new ConnectionVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }
}
