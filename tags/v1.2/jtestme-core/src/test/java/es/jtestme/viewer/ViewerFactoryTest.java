package es.jtestme.viewer;

import junit.framework.Assert;

import org.junit.Test;

import es.jtestme.viewer.Viewer;
import es.jtestme.viewer.ViewerFactory;
import es.jtestme.viewer.ViewerType;
import es.jtestme.viewer.impl.HTMLViewer;
import es.jtestme.viewer.impl.JSONViewer;
import es.jtestme.viewer.impl.PlainTextViewer;
import es.jtestme.viewer.impl.XMLViewer;

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
}
