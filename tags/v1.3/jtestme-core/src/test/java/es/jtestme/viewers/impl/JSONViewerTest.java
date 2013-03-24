package es.jtestme.viewers.impl;

import org.junit.Before;

import es.jtestme.viewers.impl.JSONViewer;

public class JSONViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new JSONViewer();
    }

}
