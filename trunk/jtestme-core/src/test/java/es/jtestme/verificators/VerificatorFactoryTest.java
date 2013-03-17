package es.jtestme.verificators;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class VerificatorFactoryTest {

    private final VerificatorFactory verificator = VerificatorFactory.getInstance();

    @Test
    public void testJTestMeVerificatorFactoryNotNull() {
        Assert.assertNotNull(verificator);
    }

    @Test
    public void testLoadVerificatorNameAndParamsNull() {
        final String name = null;
        final Map<String, String> params = null;
        Assert.assertNull(verificator.loadVerificator(name, params));
    }

    @Test
    public void testLoadVerificatorNameAndParamsEmpty() {
        final String name = "";
        final Map<String, String> params = new HashMap<String, String>();
        Assert.assertNull(verificator.loadVerificator(name, params));
    }

    @Test
    public void testLoadVerificatorNameNullAndParamsNotType() {
        final String name = null;
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        Assert.assertNull(verificator.loadVerificator(name, params));
    }

    @Test
    public void testLoadVerificatorParamsTypeNotFound() {
        final String name = "name";
        final Map<String, String> params = new HashMap<String, String>();
        params.put("type", "notfound");
        Assert.assertNull(verificator.loadVerificator(name, params));
    }

    @Test
    public void testLoadVerificatorParamsTypeOk() {
        final String name = "name";
        final Map<String, String> params = new HashMap<String, String>();
        params.put("type", "jdbc");
        Assert.assertNotNull(verificator.loadVerificator(name, params));
    }

}
