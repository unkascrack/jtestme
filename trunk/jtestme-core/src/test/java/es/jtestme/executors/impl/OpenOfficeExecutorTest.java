package es.jtestme.executors.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.jtestme.domain.JTestMeResult;

public class OpenOfficeExecutorTest {

    private OpenOfficeExecutor executor;

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
    public void testExecutorTestMeParamsNull() {
        executor = new OpenOfficeExecutor(null);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        executor = new OpenOfficeExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsHostPortNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "notfound");
        params.put("port", "21");
        executor = new OpenOfficeExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "localhost");
        params.put("port", "8100");
        executor = new OpenOfficeExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

}
