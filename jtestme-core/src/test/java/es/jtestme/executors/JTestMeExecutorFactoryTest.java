package es.jtestme.executors;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import es.jtestme.executors.JTestMeExecutorFactory;

public class JTestMeExecutorFactoryTest {

    private final JTestMeExecutorFactory executor = JTestMeExecutorFactory.getInstance();

    @Test
    public void testJTestMeExecutorFactoryNotNull() {
        Assert.assertNotNull(executor);
    }

    @Test
    public void testLoadExecutorNameAndParamsNull() {
        final String name = null;
        final Map<String, String> params = null;
        Assert.assertNull(executor.loadExecutor(name, params));
    }

    @Test
    public void testLoadExecutorNameAndParamsEmpty() {
        final String name = "";
        final Map<String, String> params = new HashMap<String, String>();
        Assert.assertNull(executor.loadExecutor(name, params));
    }

    @Test
    public void testLoadExecutorNameNullAndParamsNotType() {
        final String name = null;
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", "name");
        Assert.assertNull(executor.loadExecutor(name, params));
    }

    @Test
    public void testLoadExecutorParamsTypeNotFound() {
        final String name = "name";
        final Map<String, String> params = new HashMap<String, String>();
        params.put("type", "notfound");
        Assert.assertNull(executor.loadExecutor(name, params));
    }

    @Test
    public void testLoadExecutorParamsTypeOk() {
        final String name = "name";
        final Map<String, String> params = new HashMap<String, String>();
        params.put("type", "jdbc");
        Assert.assertNotNull(executor.loadExecutor(name, params));
    }

}
