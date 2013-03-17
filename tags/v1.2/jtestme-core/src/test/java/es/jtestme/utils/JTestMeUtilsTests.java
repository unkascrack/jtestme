package es.jtestme.utils;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.verificators.Verificator;
import es.jtestme.verificators.custom.FakeVerificator;

public class JTestMeUtilsTests {

    @Test
    public void testLoadVerificatorNull() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadVerificator(null, null, null);
        Assert.assertNull(verificator);
    }

    @Test
    public void testLoadVerificatorEmpty() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadVerificator(" ", null, null);
        Assert.assertNull(verificator);
    }

    @Test(expected = ClassNotFoundException.class)
    public void testLoadVerificatorNotFound() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadVerificator("notfound", null, null);
        Assert.assertNull(verificator);
    }

    @Test
    public void testLoadVerificatorNoConstructor() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadVerificator(JTestMeUtils.class.getName(), null, null);
        Assert.assertNull(verificator);
    }

    @Test
    public void testLoadVerificatorOk() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadVerificator(FakeVerificator.class.getName(), null, null);
        Assert.assertNotNull(verificator);
    }

}
