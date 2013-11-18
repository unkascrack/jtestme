package es.jtestme.verificators.impl;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import es.jtestme.domain.VerificatorResult;

public class JNDIVerificatorTest {

    private JNDIVerificator verificator;

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
    public void testExecuteParamsEmpty() {
        verificator = new JNDIVerificator(null);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsLookupNoExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("lookup", "java:comp/env/noexiste");
        verificator = new JNDIVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsLookupNoEnv() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("lookup", "jdbc/myresource");
        verificator = new JNDIVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsLookupExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("factory", "org.jnp.interfaces.NamingContextFactory");
        params.put("url", "jnp://localhost:1099");
        params.put("pkgs", "org.jboss.naming:org.jnp.interfaces");
        params.put("lookup", "myresource");
        verificator = new JNDIVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }
}
