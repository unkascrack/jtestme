package es.jtestme.viewer;

import static es.jtestme.viewer.ViewerType.HTML;
import static es.jtestme.viewer.ViewerType.JSON;
import static es.jtestme.viewer.ViewerType.TXT;
import static es.jtestme.viewer.ViewerType.XML;

import java.util.HashMap;
import java.util.Map;

import es.jtestme.logger.JTestMeLogger;
import es.jtestme.viewer.impl.HTMLViewer;
import es.jtestme.viewer.impl.JSONViewer;
import es.jtestme.viewer.impl.PlainTextViewer;
import es.jtestme.viewer.impl.XMLViewer;

public final class ViewerFactory {

    private static final Map<ViewerType, Viewer> VIEWERS = new HashMap<ViewerType, Viewer>();
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
    public static void registerViewer(final ViewerType viewerType, final Viewer viewer) {
        VIEWERS.put(viewerType, viewer);
    }

    /**
     * @param viewerType
     * @return
     * @throws IllegalArgumentException
     */
    public static Viewer loadViewer(final ViewerType viewerType) throws IllegalArgumentException {
        JTestMeLogger.info("JTestMe loading viewer of type: " + viewerType);
        if (!VIEWERS.containsKey(viewerType)) {
            throw new IllegalArgumentException("JTestMeViewerType not supported: " + viewerType);
        }
        return VIEWERS.get(viewerType);
    }

}
