package es.jtestme.verificators.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dumbster.smtp.SimpleSmtpServer;

import es.jtestme.domain.VerificatorResult;

public class SMTPVerificatorTest {

    private static SimpleSmtpServer smtpServer;

    private SMTPVerificator verificator;

    @BeforeClass
    public static void setUp() {
        smtpServer = SimpleSmtpServer.start(2525);
    }

    @AfterClass
    public static void tearDown() {
        smtpServer.stop();
    }

    @Test
    public void testSmtpServerNotNull() {
        Assert.assertNotNull(smtpServer);
        Assert.assertFalse(smtpServer.isStopped());
    }

    @Test
    public void testExecuteParamsNull() {
        this.verificator = new SMTPVerificator(null);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        this.verificator = new SMTPVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsHostNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "notfound");
        this.verificator = new SMTPVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "localhost");
        params.put("port", "2525");
        this.verificator = new SMTPVerificator(params);
        final VerificatorResult result = this.verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

}
