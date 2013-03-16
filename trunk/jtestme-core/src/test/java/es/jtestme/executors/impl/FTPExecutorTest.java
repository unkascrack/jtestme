package es.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.WindowsFakeFileSystem;

import es.jtestme.domain.JTestMeResult;

public class FTPExecutorTest {

    private FTPExecutor executor;

    private static FakeFtpServer fakeFtpServer;

    @BeforeClass
    public static void setUp() {
        final String dir = "C:\\data";

        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.setServerControlPort(2121);
        fakeFtpServer.addUserAccount(new UserAccount("anonymous", "anonymous", dir));
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", dir));

        final FileSystem fileSystem = new WindowsFakeFileSystem();
        fileSystem.add(new DirectoryEntry(dir));
        fakeFtpServer.setFileSystem(fileSystem);

        fakeFtpServer.start();
    }

    @AfterClass
    public static void tearDown() {
        fakeFtpServer.stop();
    }

    @Test
    public void testFakeFtpServerNotNull() {
        Assert.assertNotNull(fakeFtpServer);
        Assert.assertTrue(fakeFtpServer.isStarted());
    }

    @Test
    public void testExecutorTestMeParamsNull() {
        executor = new FTPExecutor(null);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        executor = new FTPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsHostNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "notfound");
        executor = new FTPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsUserNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "localhost");
        params.put("port", "2121");
        params.put("username", "notfound");
        params.put("password", "notfound");
        executor = new FTPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsUserAnonymous() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "localhost");
        params.put("port", "2121");
        params.put("username", "anonymous");
        params.put("password", "anonymous");
        executor = new FTPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsUserOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "localhost");
        params.put("port", "2121");
        params.put("username", "user");
        params.put("password", "password");
        executor = new FTPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }
}
