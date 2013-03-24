package es.jtestme.viewers;

public enum ViewerType {

    HTML,
    TXT,
    JSON,
    XML,
    CUSTOM;

    public static ViewerType toType(final String str) {
        return toType(str, null);
    }

    public static ViewerType toType(final String str, final ViewerType defaultType) {
        ViewerType type = null;
        try {
            type = str != null ? valueOf(str.toUpperCase()) : defaultType;
        } catch (final IllegalArgumentException e) {
            type = defaultType;
        }
        return type;
    }

    public static boolean isHTML(final ViewerType viewerType) {
        return HTML == viewerType;
    }
}
