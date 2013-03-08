package es.map.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.map.jtestme.domain.JTestMeResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config-database-test.xml" })
public class JDBCExecutorTest extends AbstractJUnit4SpringContextTests {

    final static Map<String, String> params = new HashMap<String, String>();

    private JDBCExecutor executor;

    @Before
    public void setUp() {
        executor = new JDBCExecutor(params);
    }

    @Test
    public void testJDBCExecutorNotNull() {
        Assert.assertNotNull(executor);
    }

    @Test
    public void testExecutorTestMeParamsEmpty() {
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDriverNoExists() {
        params.put("driver", "oracle.jdbc.driver.OracleDriver");
        params.put("url", "");
        params.put("username", "");
        params.put("password", "");
        params.put("testquery", "");
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDatabaseNoExists() {
        params.put("driver", "org.hsqldb.jdbcDriver");
        params.put("url", "jdbc:hsqldb:hsql://localhost/sepm_db");
        params.put("username", "sa");
        params.put("password", "");
        params.put("testquery", "select 1 from dual");
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDatabaseExists() {
        params.put("driver", "org.hsqldb.jdbcDriver");
        params.put("url", "jdbc:hsqldb:mem:dataSource");
        params.put("username", "sa");
        params.put("password", "");
        params.put("testquery", "select 1 from dual");
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDatabaseExistsWithouTestQuery() {
        params.put("driver", "org.hsqldb.jdbcDriver");
        params.put("url", "jdbc:hsqldb:mem:dataSource");
        params.put("username", "sa");
        params.put("password", "");
        params.put("testquery", "");
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

}
