package es.map.jtestme.viewer.impl;

import org.junit.Before;

public class HTMLViewerTest extends JTestMeDefaultViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new HTMLViewer();
    }
}
