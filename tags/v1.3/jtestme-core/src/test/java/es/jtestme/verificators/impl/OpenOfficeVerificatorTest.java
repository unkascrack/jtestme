package es.jtestme.verificators.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.jtestme.domain.VerificatorResult;

public class OpenOfficeVerificatorTest {

    private OpenOfficeVerificator verificator;

    private static Process sOffice;

    @BeforeClass
    public static void setUp() throws IOException, InterruptedException {
        final String[] commands = new String[] { "soffice", "-headless", "-accept=socket,host=localhost,port=8100;urp;" };
        sOffice = Runtime.getRuntime().exec(commands);
        final int code = sOffice.waitFor();
        if (code != 0) {
            throw new RuntimeException();
        }
    }

    @AfterClass
    public static void tearDown() {
        sOffice.destroy();
    }

    @Test
    public void testSOfficeNotNull() {
        Assert.assertNotNull(sOffice);
    }

    @Test
    public void testExecuteParamsNull() {
        verificator = new OpenOfficeVerificator(null);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecuteParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        verificator = new OpenOfficeVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecuteParamsHostPortNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "notfound");
        params.put("port", "21");
        verificator = new OpenOfficeVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecuteParamsOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "localhost");
        params.put("port", "8100");
        verificator = new OpenOfficeVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

}
