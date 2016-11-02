package es.jtestme.viewers.impl;

import org.junit.Before;

public class XMLViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        this.viewer = new XMLViewer();
    }

}
