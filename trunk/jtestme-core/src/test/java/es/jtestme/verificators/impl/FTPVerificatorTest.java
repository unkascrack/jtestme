package es.jtestme.verificators.impl;

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

import es.jtestme.domain.VerificatorResult;

public class FTPVerificatorTest {

    private FTPVerificator verificator;

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
    public void testExecuteParamsNull() {
        verificator = new FTPVerificator(null);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        verificator = new FTPVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsHostNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "notfound");
        verificator = new FTPVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsUserNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "localhost");
        params.put("port", "2121");
        params.put("username", "notfound");
        params.put("password", "notfound");
        verificator = new FTPVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsUserAnonymous() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "localhost");
        params.put("port", "2121");
        params.put("username", "anonymous");
        params.put("password", "anonymous");
        verificator = new FTPVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testExecuteParamsUserOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "localhost");
        params.put("port", "2121");
        params.put("username", "user");
        params.put("password", "password");
        verificator = new FTPVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testExecuteParamsExternalFtp() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("host", "ftp.kernel.org");
        verificator = new FTPVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

}
