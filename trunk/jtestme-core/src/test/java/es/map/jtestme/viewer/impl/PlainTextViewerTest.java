package es.map.jtestme.viewer.impl;

import org.junit.Before;

public class PlainTextViewerTest extends JTestMeDefaultViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new PlainTextViewer();
    }

}
