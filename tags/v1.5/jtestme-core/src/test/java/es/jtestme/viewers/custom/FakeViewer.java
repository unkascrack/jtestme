package es.jtestme.viewers.custom;

import java.util.List;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.viewers.Viewer;

public class FakeViewer implements Viewer {

    public String getContentType() {
        return null;
    }

    public String getContentViewer(final List<VerificatorResult> results) {
        return null;
    }

}
