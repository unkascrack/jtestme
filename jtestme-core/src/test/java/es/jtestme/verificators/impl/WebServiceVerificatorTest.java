package es.jtestme.verificators.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.domain.VerificatorResult;

public class WebServiceVerificatorTest {

    private WebServiceVerificator verificator;

    @Test
    public void testExecuteParamsNull() {
        this.verificator = new WebServiceVerificator(null);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        this.verificator = new WebServiceVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsEmptyRPCWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rpc");
        this.verificator = new WebServiceVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsIncorrectRPCWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rpc");
        params.put("endpoint", "http://notfound.com?wsdl");
        params.put("namespaceuri", "http://notfound.com/");
        params.put("localpart", "notfound");
        this.verificator = new WebServiceVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsOkRPCWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rpc");
        params.put("endpoint", "http://graphical.weather.gov/xml/SOAP_server/ndfdXMLserver.php?wsdl");
        params.put("namespaceuri", "http://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl");
        params.put("localpart", "ndfdXML");
        this.verificator = new WebServiceVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testExecuteParamsEmptySOAPWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "soap");
        this.verificator = new WebServiceVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsIncorrectSOAPWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "soap");
        params.put("endpoint", "http://notfound.com?wsdl");
        params.put("namespaceuri", "http://notfound.com/");
        params.put("localpart", "notfound");
        this.verificator = new WebServiceVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsOkSOAPWebService() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "soap");
        params.put("endpoint", "http://www.webservicex.net/whois.asmx?WSDL");
        params.put("namespaceuri", "http://www.webservicex.net");
        params.put("localpart", "GetWhoIS");
        this.verificator = new WebServiceVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testExecuteParamsEmptyWebServiceREST() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rest");
        this.verificator = new WebServiceVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsIncorrectWebServiceREST() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rest");
        params.put("endpoint", "http://incorrrectrestservice.com");
        this.verificator = new WebServiceVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsOkWebServiceREST() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("protocol", "rest");
        params.put("endpoint", "http://www.dtcenter.org/met/metviewer/servlet");
        this.verificator = new WebServiceVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }
}
