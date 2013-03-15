package es.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.domain.JTestMeResult;

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
    public void testExecutorTestMeParamsEmptyRPCWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rpc");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsIncorrectRPCWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rpc");
        params.put("endpoint", "http://notfound.com?wsdl");
        params.put("namespaceuri", "http://notfound.com/");
        params.put("localpart", "notfound");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsOkRPCWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rpc");
        params.put("endpoint", "http://graphical.weather.gov/xml/SOAP_server/ndfdXMLserver.php?wsdl");
        params.put("namespaceuri", "http://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl");
        params.put("localpart", "ndfdXML");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsEmptySOAPWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "soap");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsIncorrectSOAPWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "soap");
        params.put("endpoint", "http://notfound.com?wsdl");
        params.put("namespaceuri", "http://notfound.com/");
        params.put("localpart", "notfound");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsOkSOAPWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "soap");
        params.put("endpoint", "http://www.webservicex.net/whois.asmx?WSDL");
        params.put("namespaceuri", "http://www.webservicex.net");
        params.put("localpart", "GetWhoIS");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsEmptyWebServiceREST() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rest");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsIncorrectWebServiceREST() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rest");
        params.put("endpoint", "http://incorrrectrestservice.com");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsOkWebServiceREST() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rest");
        params.put("endpoint", "http://www.dtcenter.org/met/metviewer/servlet");
        executor = new WebServiceExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }
}
