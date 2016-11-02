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
        this.verificator = new CustomVerificator(null);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        this.verificator = new CustomVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsClassNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("class", "notfound");
        this.verificator = new CustomVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsClassFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("class", FakeVerificator.class.getName());
        this.verificator = new CustomVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testExecuteParamsClassFoundWithParams() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("class", FakeVerificator.class.getName());
        params.put("url", "http://www.google.com");
        this.verificator = new CustomVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }
}
