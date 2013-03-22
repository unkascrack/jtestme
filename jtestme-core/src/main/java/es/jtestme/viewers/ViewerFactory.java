package es.jtestme.viewers;

import static es.jtestme.viewers.ViewerType.HTML;
import static es.jtestme.viewers.ViewerType.JSON;
import static es.jtestme.viewers.ViewerType.TXT;
import static es.jtestme.viewers.ViewerType.XML;

import java.util.HashMap;
import java.util.Map;

import es.jtestme.logger.JTestMeLogger;
import es.jtestme.viewers.impl.HTMLViewer;
import es.jtestme.viewers.impl.JSONViewer;
import es.jtestme.viewers.impl.PlainTextViewer;
import es.jtestme.viewers.impl.XMLViewer;

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
