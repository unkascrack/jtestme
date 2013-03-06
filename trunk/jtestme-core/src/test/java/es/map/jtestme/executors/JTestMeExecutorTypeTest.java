package es.map.jtestme.executors;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class JTestMeExecutorTypeTest extends TestCase {

    @Test
    public void testTypeJDBC() {
        Assert.assertEquals(JTestMeExecutorType.toType("jdbc"), JTestMeExecutorType.JDBC);
        Assert.assertEquals(JTestMeExecutorType.toType("jdBc"), JTestMeExecutorType.JDBC);
        Assert.assertEquals(JTestMeExecutorType.toType("JDBC"), JTestMeExecutorType.JDBC);
    }

    @Test
    public void testTypeJNDI() {
        Assert.assertEquals(JTestMeExecutorType.toType("jndi"), JTestMeExecutorType.JNDI);
        Assert.assertEquals(JTestMeExecutorType.toType("Jndi"), JTestMeExecutorType.JNDI);
        Assert.assertEquals(JTestMeExecutorType.toType("JNDI"), JTestMeExecutorType.JNDI);
    }

    @Test
    public void testTypeConnection() {
        Assert.assertEquals(JTestMeExecutorType.toType("connection"), JTestMeExecutorType.CONNECTION);
        Assert.assertEquals(JTestMeExecutorType.toType("Connection"), JTestMeExecutorType.CONNECTION);
        Assert.assertEquals(JTestMeExecutorType.toType("CONNECTION"), JTestMeExecutorType.CONNECTION);
    }

    @Test
    public void testTypeCustom() {
        Assert.assertEquals(JTestMeExecutorType.toType("custom"), JTestMeExecutorType.CUSTOM);
        Assert.assertEquals(JTestMeExecutorType.toType("Custom"), JTestMeExecutorType.CUSTOM);
        Assert.assertEquals(JTestMeExecutorType.toType("CUSTOM"), JTestMeExecutorType.CUSTOM);
    }

    @Test
    public void testTypeNull() {
        Assert.assertNull(JTestMeExecutorType.toType(null));
    }

    @Test
    public void testTypeNotFound() {
        Assert.assertNull(JTestMeExecutorType.toType("notfound"));
    }
}
