package es.map.jtestme.viewer;

import java.util.List;

import es.map.jtestme.domain.JTestMeResult;

public interface JTestMeViewer {

    static final String NEW_LINE = System.getProperty("line.separator");
    static final String TAB = "\t";

    /**
     * @return
     */
    public String getExtension();

    /**
     * @return
     */
    public String getContentType();

    /**
     * @param results
     * @return
     */
    public String getContentViewer(List<JTestMeResult> results);
}
