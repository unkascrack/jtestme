package es.jtestme.utils;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import es.jtestme.verificators.Verificator;
import es.jtestme.verificators.custom.FakeVerificator;
import es.jtestme.viewers.Viewer;
import es.jtestme.viewers.impl.HTMLViewer;

public class JTestMeUtilsTests {

    @Test
    public void testloadClassNull() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadClass(null, null, null);
        Assert.assertNull(verificator);
    }

    @Test
    public void testloadClassEmpty() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadClass(" ", null, null);
        Assert.assertNull(verificator);
    }

    @Test(expected = ClassNotFoundException.class)
    public void testloadClassNotFound() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadClass("notfound", null, null);
        Assert.assertNull(verificator);
    }

    @Test
    public void testloadClassNoConstructor() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadClass(JTestMeUtils.class.getName(), null, null);
        Assert.assertNull(verificator);
    }

    @Test
    public void testloadClassOneArgumentsOk() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadClass(FakeVerificator.class.getName(), "test");
        Assert.assertNotNull(verificator);
    }

    @Test
    public void testloadClassTwoArgumentsOk() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadClass(FakeVerificator.class.getName(), null, null);
        Assert.assertNotNull(verificator);
    }

    @Test
    public void testloadClassThreeArgumentsError() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadClass(FakeVerificator.class.getName(), null, null, null);
        Assert.assertNull(verificator);
    }

    @Test
    public void testloadClassEmptyNoArguments() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadClass(" ");
        Assert.assertNull(verificator);
    }

    @Test(expected = ClassNotFoundException.class)
    public void testloadClassNotFoundNoArguments() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadClass("notfound");
        Assert.assertNull(verificator);
    }

    @Test
    public void testloadClassNoConstructorNoArguments() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadClass(JTestMeUtils.class.getName());
        Assert.assertNull(verificator);
    }

    @Test
    public void testloadClassOkNoArguments() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Viewer viewer = JTestMeUtils.loadClass(HTMLViewer.class.getName());
        Assert.assertNotNull(viewer);
    }

    @Test
    public void testloadVerificatorClassNull() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadVerificatorClass(null, null, null);
        Assert.assertNull(verificator);
    }

    @Test
    public void testloadVerificatorClassEmpty() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadVerificatorClass(" ", null, null);
        Assert.assertNull(verificator);
    }

    @Test(expected = ClassNotFoundException.class)
    public void testloadVerificatorClassNotFound() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadVerificatorClass("notfound", null, null);
        Assert.assertNull(verificator);
    }

    @Test
    public void testloadVerificatorClassNoConstructor() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadVerificatorClass(JTestMeUtils.class.getName(), null, null);
        Assert.assertNull(verificator);
    }

    @Test
    public void testloadVerificatorClassOk() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Verificator verificator = JTestMeUtils.loadVerificatorClass(FakeVerificator.class.getName(), null, null);
        Assert.assertNotNull(verificator);
    }

}
