package es.jtestme.viewer;

import junit.framework.Assert;

import org.junit.Test;

import es.jtestme.viewer.JTestMeViewer;
import es.jtestme.viewer.JTestMeViewerFactory;
import es.jtestme.viewer.JTestMeViewerType;
import es.jtestme.viewer.impl.HTMLViewer;
import es.jtestme.viewer.impl.JSONViewer;
import es.jtestme.viewer.impl.PlainTextViewer;
import es.jtestme.viewer.impl.XMLViewer;

public class JTestMeViewerFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void testLoadViewerTypeNull() {
        JTestMeViewerFactory.loadViewer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadViewerTypeNotFound() {
        JTestMeViewerFactory.loadViewer(JTestMeViewerType.toType("notfound"));
    }

    @Test
    public void testLoadViewerTypeHTML() {
        final JTestMeViewer viewer = JTestMeViewerFactory.loadViewer(JTestMeViewerType.HTML);
        Assert.assertNotNull(viewer);
        Assert.assertEquals(viewer.getClass(), HTMLViewer.class);
    }

    @Test
    public void testLoadViewerTypeXML() {
        final JTestMeViewer viewer = JTestMeViewerFactory.loadViewer(JTestMeViewerType.XML);
        Assert.assertNotNull(viewer);
        Assert.assertEquals(viewer.getClass(), XMLViewer.class);
    }

    @Test
    public void testLoadViewerTypeTXT() {
        final JTestMeViewer viewer = JTestMeViewerFactory.loadViewer(JTestMeViewerType.TXT);
        Assert.assertNotNull(viewer);
        Assert.assertEquals(viewer.getClass(), PlainTextViewer.class);
    }

    @Test
    public void testLoadViewerTypeJSON() {
        final JTestMeViewer viewer = JTestMeViewerFactory.loadViewer(JTestMeViewerType.JSON);
        Assert.assertNotNull(viewer);
        Assert.assertEquals(viewer.getClass(), JSONViewer.class);
    }
}
