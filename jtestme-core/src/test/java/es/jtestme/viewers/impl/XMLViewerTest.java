package es.jtestme.viewers.impl;

import org.junit.Before;

import es.jtestme.viewers.impl.XMLViewer;

public class XMLViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new XMLViewer();
    }

}
