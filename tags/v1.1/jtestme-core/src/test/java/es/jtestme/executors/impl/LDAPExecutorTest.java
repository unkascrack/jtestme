package es.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;

import es.jtestme.domain.JTestMeResult;

public class LDAPExecutorTest {

    private static InMemoryDirectoryServer server;

    private LDAPExecutor executor;

    @BeforeClass
    public static void setUp() throws Exception {
        final InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("cn=admin,dc=es");
        final InMemoryListenerConfig listenerConfig = new InMemoryListenerConfig("test", null, 33390, null, null, null);
        config.setListenerConfigs(listenerConfig);
        config.addAdditionalBindCredentials("cn=admin,dc=es", "cambiame");
        server = new InMemoryDirectoryServer(config);
        server.startListening();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.shutDown(true);
    }

    @Test
    public void testLDAPServerNotNull() {
        Assert.assertNotNull(server);
    }

    @Test
    public void testExecutorTestMeParamsURLNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "ldap://ldapnoexiste:33390");
        executor = new LDAPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsURLFoundPrincipalNull() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "ldap://localhost:33390");
        executor = new LDAPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsURLFoundPrincipalCredentialsIncorrect() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "ldap://localhost:33390");
        params.put("principal", "notfound");
        params.put("credentials", "notfound");
        executor = new LDAPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsURLPrincipalOkCredentialsIncorrect() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "ldap://localhost:33390");
        params.put("principal", "cn=admin,dc=es");
        executor = new LDAPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsURLPrincipalCredentialsOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "ldap://localhost:33390");
        params.put("principal", "cn=admin,dc=es");
        params.put("credentials", "cambiame");
        executor = new LDAPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }
}
