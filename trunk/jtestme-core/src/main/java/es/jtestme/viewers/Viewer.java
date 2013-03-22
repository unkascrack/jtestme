package es.jtestme.viewers;

import java.util.List;

import es.jtestme.domain.VerificatorResult;

public interface Viewer {

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
    public String getContentViewer(List<VerificatorResult> results);
}
