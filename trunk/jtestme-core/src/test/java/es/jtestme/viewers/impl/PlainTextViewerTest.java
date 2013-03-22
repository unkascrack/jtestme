package es.jtestme.viewers.impl;

import org.junit.Before;

import es.jtestme.viewers.impl.PlainTextViewer;

public class PlainTextViewerTest extends AbstractViewerTest {

    @Override
    @Before
    public void setUp() {
        viewer = new PlainTextViewer();
    }

}
