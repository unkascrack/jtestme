package es.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.executors.impl.JNDIExecutor;

public class JNDIExecutorTest {

    private JNDIExecutor executor;

    private static SimpleNamingContextBuilder builder;

    @BeforeClass
    public static void setUp() throws IllegalStateException, NamingException {
        builder = new SimpleNamingContextBuilder();
        builder.bind(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        builder.bind(Context.PROVIDER_URL, "jnp://localhost:1099");
        builder.bind(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        builder.bind("myresource", new Object());
        builder.activate();
    }

    @AfterClass
    public static void tearDown() {
        builder.clear();
        builder.deactivate();
    }

    @Test
    public void testExecutorTestMeParamsEmpty() {
        executor = new JNDIExecutor(null);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsLookupNoExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("lookup", "java:comp/env/noexiste");
        executor = new JNDIExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsLookupNoEnv() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("lookup", "jdbc/myresource");
        executor = new JNDIExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsLookupExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("factory", "org.jnp.interfaces.NamingContextFactory");
        params.put("url", "jnp://localhost:1099");
        params.put("pkgs", "org.jboss.naming:org.jnp.interfaces");
        params.put("lookup", "myresource");
        executor = new JNDIExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }
}
