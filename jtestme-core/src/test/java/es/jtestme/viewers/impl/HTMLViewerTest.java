package es.jtestme.viewers.impl;

import org.junit.Before;

public class HTMLViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        this.viewer = new HTMLViewer();
    }
}
