package es.jtestme.viewers.impl;

import org.junit.Before;

import es.jtestme.viewers.impl.HTMLViewer;

public class HTMLViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new HTMLViewer();
    }
}
