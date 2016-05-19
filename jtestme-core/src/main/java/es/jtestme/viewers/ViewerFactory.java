package es.jtestme.viewers;

import static es.jtestme.viewers.ViewerType.CUSTOM;
import static es.jtestme.viewers.ViewerType.HTML;
import static es.jtestme.viewers.ViewerType.JSON;
import static es.jtestme.viewers.ViewerType.TXT;
import static es.jtestme.viewers.ViewerType.XML;

import java.util.HashMap;
import java.util.Map;

import es.jtestme.logger.JTestMeLogger;
import es.jtestme.utils.JTestMeUtils;
import es.jtestme.viewers.impl.CustomViewer;
import es.jtestme.viewers.impl.HTMLViewer;
import es.jtestme.viewers.impl.JSONViewer;
import es.jtestme.viewers.impl.PlainTextViewer;
import es.jtestme.viewers.impl.XMLViewer;

public final class ViewerFactory {

    private ViewerFactory() {
    }

    private static final Map<ViewerType, Viewer> VIEWERS = new HashMap<ViewerType, Viewer>();
    static {
        VIEWERS.put(HTML, new HTMLViewer());
        VIEWERS.put(XML, new XMLViewer());
        VIEWERS.put(TXT, new PlainTextViewer());
        VIEWERS.put(JSON, new JSONViewer());
        VIEWERS.put(CUSTOM, new CustomViewer());
    }

    /**
     * @param viewerType
     * @param viewer
     */
    public static void registerViewer(final ViewerType viewerType, final Viewer viewer) {
        if (viewerType != null && viewer != null) {
            JTestMeLogger
                    .debug("JTestMe register viewer type " + viewerType + " for class: " + viewer.getClass().getName());
            VIEWERS.put(viewerType, viewer);
        }
    }

    /**
     * @param viewerType
     * @param viewerClass
     */
    public static void registerViewer(final ViewerType viewerType, final String viewerClass) {
        if (viewerType != null && viewerClass != null && viewerClass.trim().length() > 0) {
            try {
                final Viewer viewer = JTestMeUtils.loadClass(viewerClass);
                registerViewer(viewerType, viewer);
            } catch (final Throwable e) {
                JTestMeLogger.warn("JTestMe error loading " + viewerType + " viewer of class " + viewerClass + ": "
                        + e.getMessage());
            }
        }
    }

    /**
     * @param viewerType
     * @return
     * @throws IllegalArgumentException
     */
    public static Viewer loadViewer(final ViewerType viewerType) throws IllegalArgumentException {
        JTestMeLogger.debug("JTestMe loading viewer of type: " + viewerType);
        if (!VIEWERS.containsKey(viewerType)) {
            throw new IllegalArgumentException("JTestMeViewerType not supported: " + viewerType);
        }
        return VIEWERS.get(viewerType);
    }
}
