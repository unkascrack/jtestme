package es.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dumbster.smtp.SimpleSmtpServer;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.executors.impl.SMTPExecutor;

public class SMTPExecutorTest {

    private static SimpleSmtpServer smtpServer;

    private SMTPExecutor executor;

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
    public void testExecutorTestMeParamsNull() {
        executor = new SMTPExecutor(null);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        executor = new SMTPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsHostNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "notfound");
        executor = new SMTPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "localhost");
        params.put("port", "2525");
        executor = new SMTPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

}
