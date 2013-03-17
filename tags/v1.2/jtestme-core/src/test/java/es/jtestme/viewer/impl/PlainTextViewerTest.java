package es.jtestme.viewer.impl;

import org.junit.Before;

import es.jtestme.viewer.impl.PlainTextViewer;

public class PlainTextViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new PlainTextViewer();
    }

}
