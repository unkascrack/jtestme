package es.map.jtestme.viewer;

import static es.map.jtestme.viewer.JTestMeViewerType.HTML;
import static es.map.jtestme.viewer.JTestMeViewerType.JSON;
import static es.map.jtestme.viewer.JTestMeViewerType.TXT;
import static es.map.jtestme.viewer.JTestMeViewerType.XML;

import java.util.HashMap;
import java.util.Map;

import es.map.jtestme.logger.JTestMeLogger;
import es.map.jtestme.viewer.impl.HTMLViewer;
import es.map.jtestme.viewer.impl.JSONViewer;
import es.map.jtestme.viewer.impl.PlainTextViewer;
import es.map.jtestme.viewer.impl.XMLViewer;

public class JTestMeViewerFactory {

    private static final Map<JTestMeViewerType, JTestMeViewer> VIEWERS = new HashMap<JTestMeViewerType, JTestMeViewer>();
    static {
        VIEWERS.put(HTML, new HTMLViewer());
        VIEWERS.put(XML, new XMLViewer());
        VIEWERS.put(TXT, new PlainTextViewer());
        VIEWERS.put(JSON, new JSONViewer());
    }

    /**
     * @param viewerType
     * @param viewer
     */
    public static void registerViewer(final JTestMeViewerType viewerType, final JTestMeViewer viewer) {
        VIEWERS.put(viewerType, viewer);
    }

    /**
     * @param viewerType
     * @return
     * @throws IllegalArgumentException
     */
    public static JTestMeViewer loadViewer(final JTestMeViewerType viewerType) throws IllegalArgumentException {
        JTestMeLogger.info("JTestMe loading viewer of type: " + viewerType);
        if (!VIEWERS.containsKey(viewerType)) {
            throw new IllegalArgumentException("JTestMeViewerType not supported: " + viewerType);
        }
        return VIEWERS.get(viewerType);
    }

}
