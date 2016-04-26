package es.jtestme.domain;

import org.junit.Test;

import junit.framework.Assert;

public class VerificatorResultTest {

    @Test
    public void testGetSuccessStringValueDefault() {
        final VerificatorResult result = new VerificatorResult();
        final String success = result.getSuccessString();
        Assert.assertNotNull(success);
        Assert.assertEquals(success, "ERROR");
    }

    @Test
    public void testGetSuccessStringValueFalse() {
        final VerificatorResult result = new VerificatorResult();
        result.setSuccess(false);
        final String success = result.getSuccessString();
        Assert.assertNotNull(success);
        Assert.assertEquals(success, "ERROR");
    }

    @Test
    public void testGetSuccessStringTrue() {
        final VerificatorResult result = new VerificatorResult();
        result.setSuccess(true);
        final String success = result.getSuccessString();
        Assert.assertNotNull(success);
        Assert.assertEquals(success, "OK");
    }

    @Test
    public void testGetCauseStringValueNull() {
        final VerificatorResult result = new VerificatorResult();
        result.setCause(null);
        final String cause = result.getCauseString();
        Assert.assertNotNull(cause);
        Assert.assertTrue(cause.length() == 0);
    }

    @Test
    public void testGetCauseStringValueDefault() {
        final VerificatorResult result = new VerificatorResult();
        final String cause = result.getCauseString();
        Assert.assertNotNull(cause);
        Assert.assertTrue(cause.length() == 0);
    }

    @Test
    public void testGetCauseStringValueNotNull() {
        final VerificatorResult result = new VerificatorResult();
        result.setCause(new IllegalArgumentException("mi error"));
        final String cause = result.getCauseString();
        Assert.assertNotNull(cause);
        Assert.assertFalse(cause.length() == 0);
    }
}
