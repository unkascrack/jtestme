package es.map.jtestme.viewer.impl;

import java.util.List;

import es.map.jtestme.domain.JTestMeResult;
import es.map.jtestme.viewer.JTestMeViewer;

public class JSONViewer implements JTestMeViewer {

    public String getExtension() {
        return ".json";
    }

    public String getContentType() {
        return "application/json";
    }

    public String getContentViewer(final List<JTestMeResult> results) {
        // TODO Auto-generated method stub
        return null;
    }

}
