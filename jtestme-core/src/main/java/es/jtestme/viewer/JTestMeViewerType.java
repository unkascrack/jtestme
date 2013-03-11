package es.jtestme.viewer;

public enum JTestMeViewerType {

    HTML,
    TXT,
    JSON,
    XML;

    public static JTestMeViewerType toType(final String str) {
        return toType(str, null);
    }

    public static JTestMeViewerType toType(final String str, final JTestMeViewerType defaultType) {
        JTestMeViewerType type = null;
        try {
            type = str != null ? valueOf(str.toUpperCase()) : defaultType;
        } catch (final IllegalArgumentException e) {
            type = defaultType;
        }
        return type;
    }
    
    public static boolean isHTML(JTestMeViewerType viewerType) {
    	return HTML == viewerType;
    }
}
