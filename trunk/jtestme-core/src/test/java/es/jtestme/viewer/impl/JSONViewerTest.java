package es.jtestme.viewer.impl;

import org.junit.Before;

import es.jtestme.viewer.impl.JSONViewer;

public class JSONViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new JSONViewer();
    }

}
