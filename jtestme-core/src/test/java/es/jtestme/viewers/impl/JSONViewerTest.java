package es.jtestme.viewers.impl;

import org.junit.Before;

public class JSONViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new JSONViewer();
    }

}
