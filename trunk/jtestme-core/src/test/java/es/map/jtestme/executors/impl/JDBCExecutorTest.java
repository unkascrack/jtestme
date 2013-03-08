package es.map.jtestme.executors.impl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.map.jtestme.domain.JTestMeResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config-database-test.xml" })
public class JDBCExecutorTest extends AbstractJUnit4SpringContextTests {

    private JDBCExecutor executor;

    @Test
    public void testExecutorTestMeParamsEmpty() {
        executor = new JDBCExecutor(null);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDriverNoExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("driver", "oracle.jdbc.driver.OracleDriver");
        params.put("url", "");
        params.put("username", "");
        params.put("password", "");
        params.put("testquery", "");
        executor = new JDBCExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDatabaseNoExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("driver", "org.hsqldb.jdbcDriver");
        params.put("url", "jdbc:hsqldb:hsql://localhost/sepm_db");
        params.put("username", "sa");
        params.put("password", "");
        params.put("testquery", "select 1 from dual");
        executor = new JDBCExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDatabaseExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("driver", "org.hsqldb.jdbcDriver");
        params.put("url", "jdbc:hsqldb:mem:dataSource");
        params.put("username", "sa");
        params.put("password", "");
        params.put("testquery", "select 1 from dual");
        executor = new JDBCExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

    @Test
    public void testExecutorTestMeParamsDatabaseExistsWithouTestQuery() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("driver", "org.hsqldb.jdbcDriver");
        params.put("url", "jdbc:hsqldb:mem:dataSource");
        params.put("username", "sa");
        params.put("password", "");
        params.put("testquery", "");
        executor = new JDBCExecutor(params);
        final JTestMeResult result = executor.executeTestMe();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuscess());
    }

}
