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
     * @param type
     * @return
     */
    public static JTestMeViewer loadViewer(final String type) {
        JTestMeLogger.info("JTestMe loading viewer of type: " + type);
        final JTestMeViewerType viewerType = JTestMeViewerType.toType(type, HTML);
        return VIEWERS.get(viewerType);
    }

}
