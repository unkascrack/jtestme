package es.map.jtestme.viewer.impl;

import org.junit.Before;

public class XMLViewerTest extends JTestMeDefaultViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new XMLViewer();
    }

}
