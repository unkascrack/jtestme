package es.jtestme.viewers.impl;

import java.util.List;

import es.jtestme.domain.VerificatorResult;

public class CustomViewer extends AbstractViewer {

    public String getContentType() {
        return "text/plain";
    }

    public String getContentViewer(final List<VerificatorResult> results) {
        return "Custom viewer not implemented!!!";
    }

}
