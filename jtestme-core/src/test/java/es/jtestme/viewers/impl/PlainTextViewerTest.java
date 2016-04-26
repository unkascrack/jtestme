package es.jtestme.viewers.impl;

import org.junit.Before;

public class PlainTextViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new PlainTextViewer();
    }

}
