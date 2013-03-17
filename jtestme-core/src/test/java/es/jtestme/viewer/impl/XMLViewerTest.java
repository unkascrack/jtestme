package es.jtestme.viewer.impl;

import org.junit.Before;

import es.jtestme.viewer.impl.XMLViewer;

public class XMLViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new XMLViewer();
    }

}
