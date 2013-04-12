package es.jtestme.viewers.impl;

import org.junit.Before;

public class CustomViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new CustomViewer();
    }

}
