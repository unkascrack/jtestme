package es.jtestme.verificators;

import org.junit.Assert;
import org.junit.Test;

public class VerificatorTypeTest {

    @Test
    public void testTypeJDBC() {
        Assert.assertEquals(VerificatorType.toType("jdbc"), VerificatorType.JDBC);
        Assert.assertEquals(VerificatorType.toType("jdBc"), VerificatorType.JDBC);
        Assert.assertEquals(VerificatorType.toType("JDBC"), VerificatorType.JDBC);
    }

    @Test
    public void testTypeJNDI() {
        Assert.assertEquals(VerificatorType.toType("jndi"), VerificatorType.JNDI);
        Assert.assertEquals(VerificatorType.toType("Jndi"), VerificatorType.JNDI);
        Assert.assertEquals(VerificatorType.toType("JNDI"), VerificatorType.JNDI);
    }

    @Test
    public void testTypeConnection() {
        Assert.assertEquals(VerificatorType.toType("connection"), VerificatorType.CONNECTION);
        Assert.assertEquals(VerificatorType.toType("Connection"), VerificatorType.CONNECTION);
        Assert.assertEquals(VerificatorType.toType("CONNECTION"), VerificatorType.CONNECTION);
    }

    @Test
    public void testTypeCustom() {
        Assert.assertEquals(VerificatorType.toType("custom"), VerificatorType.CUSTOM);
        Assert.assertEquals(VerificatorType.toType("Custom"), VerificatorType.CUSTOM);
        Assert.assertEquals(VerificatorType.toType("CUSTOM"), VerificatorType.CUSTOM);
    }

    @Test
    public void testTypeNull() {
        Assert.assertNull(VerificatorType.toType(null));
    }

    @Test
    public void testTypeNotFound() {
        Assert.assertNull(VerificatorType.toType("notfound"));
    }
}
