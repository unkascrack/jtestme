package es.jtestme.viewers;

import java.util.List;

import es.jtestme.domain.VerificatorResult;

public interface Viewer {

    String NEW_LINE = System.getProperty("line.separator");
    String TAB = "\t";

    /**
     * @return
     */
    String getContentType();

    /**
     * @param results
     * @return
     */
    String getContentViewer(List<VerificatorResult> results);
}
