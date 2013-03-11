package es.jtestme.viewer.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.viewer.impl.JTestMeDefaultViewer;

public abstract class JTestMeDefaultViewerTest {

    protected JTestMeDefaultViewer viewer;

    @Before
    public abstract void setUp();

    @Test
    public void testViewerNotNull() {
        Assert.assertNotNull(viewer);
    }

    @Test
    public void testGetHostNameNotNull() {
        Assert.assertNotNull(viewer.getHostName());
    }

    @Test
    public void testGetCurrentDateAndTimeNull() {
        Assert.assertNotNull(viewer.getCurrentDateAndTime());
    }

    @Test
    public void testGetContentTypeNotNull() {
        Assert.assertNotNull(viewer.getContentType());
    }

    @Test
    public void testGetExtensionNotNull() {
        Assert.assertNotNull(viewer.getExtension());
    }

    @Test
    public void testGetContentViewerResultsNull() {
        final List<JTestMeResult> results = null;
        Assert.assertNotNull(viewer.getContentViewer(results));
    }

    @Test
    public void testGetContentViewerResultsEmpty() {
        final List<JTestMeResult> results = new ArrayList<JTestMeResult>();
        Assert.assertNotNull(viewer.getContentViewer(results));
    }

    @Test
    public void testGetContentViewerResultsNotEmpty() {
        final List<JTestMeResult> results = new ArrayList<JTestMeResult>();
        results.add(new JTestMeResult());
        Assert.assertNotNull(viewer.getContentViewer(results));
    }
}
