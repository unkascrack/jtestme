package es.jtestme.verificators.impl;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.jtestme.domain.VerificatorResult;
import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config-database-test.xml"})
public class DatasourceVerificatorTest extends AbstractJUnit4SpringContextTests {

    private DatasourceVerificator verificator;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() throws IllegalStateException, NamingException {
        final SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        builder.bind("java:comp/env/jdbc/mydatasource", dataSource);
        builder.activate();
    }

    @Test
    public void testDatasourceNotNull() {
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void testExecuteParamsEmpty() {
        verificator = new DatasourceVerificator(null);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsDatasourceNoExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("datasource", "java:comp/env/noexiste");
        params.put("testquery", "");
        verificator = new DatasourceVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsDatasourceNoEnv() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("datasource", "jdbc/mydatasource");
        params.put("testquery", "");
        verificator = new DatasourceVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteParamsDatasourceExists() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("datasource", "java:comp/env/jdbc/mydatasource");
        params.put("testquery", "select 1 from dual");
        verificator = new DatasourceVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testExecuteParamsDatasourceExistsWithouTestQuery() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("datasource", "java:comp/env/jdbc/mydatasource");
        params.put("testquery", "");
        verificator = new DatasourceVerificator(params);
        final VerificatorResult result = verificator.execute();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isSuccess());
    }
}
