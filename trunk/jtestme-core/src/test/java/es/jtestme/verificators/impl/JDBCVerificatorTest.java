package es.jtestme.verificators.impl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.jtestme.domain.VerificatorResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config-database-test.xml" })
public class JDBCVerificatorTest extends AbstractJUnit4SpringContextTests {

    private JDBCVerificator verificator;

    @Test
    public void testExecuteParamsNull() {
        verificator = new JDBCVerificator(null);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsEmpty() {
        final Map<String, String> params = new HashMap<String, String>();
        verificator = new JDBCVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsDriverNoExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("driver", "oracle.jdbc.driver.OracleDriver");
        params.put("url", "");
        params.put("username", "");
        params.put("password", "");
        params.put("testquery", "");
        verificator = new JDBCVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsDatabaseNoExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("driver", "org.hsqldb.jdbcDriver");
        params.put("url", "jdbc:hsqldb:hsql://localhost/sepm_db");
        params.put("username", "sa");
        params.put("password", "");
        params.put("testquery", "select 1 from dual");
        verificator = new JDBCVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsDatabaseExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("driver", "org.hsqldb.jdbcDriver");
        params.put("url", "jdbc:hsqldb:mem:dataSource");
        params.put("username", "sa");
        params.put("password", "");
        params.put("testquery", "select 1 from dual");
        verificator = new JDBCVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testExecuteParamsDatabaseExistsWithouTestQuery() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("driver", "org.hsqldb.jdbcDriver");
        params.put("url", "jdbc:hsqldb:mem:dataSource");
        params.put("username", "sa");
        params.put("password", "");
        params.put("testquery", "");
        verificator = new JDBCVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

}
