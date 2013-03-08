package es.map.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.map.jtestme.domain.JTestMeResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config-database-test.xml" })
public class JNDIExecutorTest extends AbstractJUnit4SpringContextTests {

    private static final Map<String, String> params = new HashMap<String, String>();

    private JNDIExecutor executor;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() throws IllegalStateException, NamingException {
        executor = new JNDIExecutor(params);
        final SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        builder.bind("java:comp/env/jdbc/mydatasource", dataSource);
        builder.activate();
    }

    @Test
    public void testJNDIExecutorNotNull() {
        Assert.assertNotNull(executor);
    }

    @Test
    public void testExecutorTestMeParamsEmpty() {
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDatasourceNoExists() {
        params.put("datasource", "java:comp/env/noexiste");
        params.put("testquery", "");
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDatasourceNoEnv() {
        params.put("datasource", "jdbc/mydatasource");
        params.put("testquery", "");
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDatasourceExists() {
        params.put("datasource", "java:comp/env/jdbc/mydatasource");
        params.put("testquery", "select 1 from dual");
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDatasourceExistsWithouTestQuery() {
        params.put("datasource", "java:comp/env/jdbc/mydatasource");
        params.put("testquery", "");
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }
}
