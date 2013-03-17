package es.jtestme.viewer.impl;

import org.junit.Before;

import es.jtestme.viewer.impl.HTMLViewer;

public class HTMLViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new HTMLViewer();
    }
}
