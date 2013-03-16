package es.jtestme.utils;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.executors.JTestMeExecutor;
import es.jtestme.executors.impl.FakeExecutor;

public class JTestMeUtilsTests {

    @Test
    public void testLoadExecutorNull() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final JTestMeExecutor executor = JTestMeUtils.loadExecutor(null, null, null);
        Assert.assertNull(executor);
    }

    @Test
    public void testLoadExecutorEmpty() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final JTestMeExecutor executor = JTestMeUtils.loadExecutor(" ", null, null);
        Assert.assertNull(executor);
    }

    @Test(expected = ClassNotFoundException.class)
    public void testLoadExecutorNotFound() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final JTestMeExecutor executor = JTestMeUtils.loadExecutor("notfound", null, null);
        Assert.assertNull(executor);
    }

    @Test
    public void testLoadExecutorNoConstructor() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final JTestMeExecutor executor = JTestMeUtils.loadExecutor(JTestMeUtils.class.getName(), null, null);
        Assert.assertNull(executor);
    }

    @Test
    public void testLoadExecutorOk() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final JTestMeExecutor executor = JTestMeUtils.loadExecutor(FakeExecutor.class.getName(), null, null);
        Assert.assertNotNull(executor);
    }

}
