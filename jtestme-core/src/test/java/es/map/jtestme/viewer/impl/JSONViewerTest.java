package es.map.jtestme.viewer.impl;

import org.junit.Before;

public class JSONViewerTest extends JTestMeDefaultViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new JSONViewer();
    }

}
