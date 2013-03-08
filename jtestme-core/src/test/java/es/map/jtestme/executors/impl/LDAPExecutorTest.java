package es.map.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import es.map.jtestme.domain.JTestMeResult;

public class LDAPExecutorTest {

    private LDAPExecutor executor;

    @Test
    public void testExecutorTestMeParamsEmpty() {
        executor = new LDAPExecutor(null);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsURLNotFound() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "ldap://ldapnoexiste:389/");
        executor = new LDAPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsURLFoundPrincipalNull() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "ldap://ldappre.map.es:389");
        executor = new LDAPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsURLFoundPrincipalCredentialsIncorrect() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "ldap://ldappre.map.es:389");
        params.put("principal", "notfound");
        params.put("credentials", "notfound");
        executor = new LDAPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsURLPrincipalOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "ldap://ldappre.map.es:389");
        params.put("principal", "cn=admin,dc=map,dc=es");
        executor = new LDAPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsURLPrincipalCredentialsOk() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("url", "ldap://ldappre.map.es:389");
        params.put("principal", "cn=admin,dc=map,dc=es");
        params.put("credentials", "cambiame");
        executor = new LDAPExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }
}
