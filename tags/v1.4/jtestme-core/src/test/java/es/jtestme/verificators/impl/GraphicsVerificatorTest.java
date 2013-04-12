package es.jtestme.verificators.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.domain.VerificatorResult;

public class GraphicsVerificatorTest {

    private GraphicsVerificator verificator;

    @Test
    public void testExecuteParamsNull() {
        verificator = new GraphicsVerificator(null);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testExecuteParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        verificator = new GraphicsVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

}
