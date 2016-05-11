package es.jtestme.viewers;

import org.junit.Test;

import es.jtestme.verificators.custom.FakeVerificator;
import es.jtestme.viewers.custom.FakeViewer;
import es.jtestme.viewers.impl.CustomViewer;
import es.jtestme.viewers.impl.HTMLViewer;
import es.jtestme.viewers.impl.JSONViewer;
import es.jtestme.viewers.impl.PlainTextViewer;
import es.jtestme.viewers.impl.XMLViewer;
import org.junit.Assert;

public class ViewerFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void testLoadViewerTypeNull() {
        ViewerFactory.loadViewer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadViewerTypeNotFound() {
        ViewerFactory.loadViewer(ViewerType.toType("notfound"));
    }

    @Test
    public void testLoadViewerTypeHTML() {
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.HTML);
        Assert.assertNotNull(viewer);
        Assert.assertEquals(viewer.getClass(), HTMLViewer.class);
    }

    @Test
    public void testLoadViewerTypeXML() {
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.XML);
        Assert.assertNotNull(viewer);
        Assert.assertEquals(viewer.getClass(), XMLViewer.class);
    }

    @Test
    public void testLoadViewerTypeTXT() {
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.TXT);
        Assert.assertNotNull(viewer);
        Assert.assertEquals(viewer.getClass(), PlainTextViewer.class);
    }

    @Test
    public void testLoadViewerTypeJSON() {
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.JSON);
        Assert.assertNotNull(viewer);
        Assert.assertEquals(viewer.getClass(), JSONViewer.class);
    }

    @Test
    public void testRegisterClassViewerNull() {
        ViewerFactory.registerViewer(null, (String) null);
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.CUSTOM);
        Assert.assertNotNull(viewer);
    }

    @Test
    public void testRegisterClassViewerEmpty() {
        ViewerFactory.registerViewer(ViewerType.CUSTOM, " ");
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.CUSTOM);
        Assert.assertNotNull(viewer);
    }

    @Test
    public void testRegisterClassViewerClassNotFound() {
        ViewerFactory.registerViewer(ViewerType.CUSTOM, "classnotfound");
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.CUSTOM);
        Assert.assertNotNull(viewer);
    }

    @Test
    public void testRegisterClassViewerClassIncorrect() {
        ViewerFactory.registerViewer(ViewerType.CUSTOM, FakeVerificator.class.getName());
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.CUSTOM);
        Assert.assertNotNull(viewer);
        Assert.assertFalse(viewer.getClass().equals(FakeVerificator.class));
    }

    @Test
    public void testRegisterClassViewerClassOk() {
        ViewerFactory.registerViewer(ViewerType.CUSTOM, FakeViewer.class.getName());
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.CUSTOM);
        Assert.assertNotNull(viewer);
        Assert.assertEquals(viewer.getClass(), FakeViewer.class);
    }

    @Test
    public void testRegisterViewerNull() {
        ViewerFactory.registerViewer(null, (Viewer) null);
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.CUSTOM);
        Assert.assertNotNull(viewer);
    }

    @Test
    public void testRegisterViewerEmpty() {
        ViewerFactory.registerViewer(ViewerType.CUSTOM, (Viewer) null);
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.CUSTOM);
        Assert.assertNotNull(viewer);
    }

    @Test
    public void testRegisterViewerClassOk() {
        ViewerFactory.registerViewer(ViewerType.CUSTOM, new CustomViewer());
        final Viewer viewer = ViewerFactory.loadViewer(ViewerType.CUSTOM);
        Assert.assertNotNull(viewer);
        Assert.assertEquals(viewer.getClass(), CustomViewer.class);
    }
}
