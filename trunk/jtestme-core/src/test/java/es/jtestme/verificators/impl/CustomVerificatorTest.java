package es.jtestme.verificators.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.verificators.custom.FakeVerificator;

public class CustomVerificatorTest {

    private CustomVerificator verificator;

    @Test
    public void testExecuteParamsNull() {
        verificator = new CustomVerificator(null);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecuteParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        verificator = new CustomVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecuteParamsClassNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("class", "notfound");
        verificator = new CustomVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecuteParamsClassFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("class", FakeVerificator.class.getName());
        verificator = new CustomVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecuteParamsClassFoundWithParams() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("class", FakeVerificator.class.getName());
        params.put("url", "http://www.google.com");
        verificator = new CustomVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }
}
