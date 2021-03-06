package es.jtestme.viewers.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.jtestme.domain.VerificatorResult;

public abstract class AbstractViewerTest {

    protected AbstractViewer viewer;

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
    public void testGetContentViewerResultsNull() {
        final List<VerificatorResult> results = null;
        Assert.assertNotNull(viewer.getContentViewer(results));
    }

    @Test
    public void testGetContentViewerResultsEmpty() {
        final List<VerificatorResult> results = new ArrayList<VerificatorResult>();
        Assert.assertNotNull(viewer.getContentViewer(results));
    }

    @Test
    public void testGetContentViewerResultsNotEmpty() {
        final List<VerificatorResult> results = new ArrayList<VerificatorResult>();
        results.add(new VerificatorResult());
        Assert.assertNotNull(viewer.getContentViewer(results));
    }
}
