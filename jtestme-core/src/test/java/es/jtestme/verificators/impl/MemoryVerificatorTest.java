package es.jtestme.verificators.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.domain.VerificatorResult;

public class MemoryVerificatorTest {

    private MemoryVerificator verificator;

    @Test
    public void execute_whenParamsIsNull_should() {
        this.verificator = new MemoryVerificator(null);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void execute_whenParamsIsEmpty_shouldReturnNoSuccess() {
        this.verificator = new MemoryVerificator(new HashMap<String, String>());
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void execute_whenParamsMemoryTypeIsEmpty_shouldReturnNoSuccess() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("memorytype", "");
        params.put("minsize", "100");
        this.verificator = new MemoryVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void execute_whenParamsMinSizeIsEmpty_shouldReturnNoSuccess() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("memorytype", "pergem");
        params.put("minsize", "");
        this.verificator = new MemoryVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }
}
