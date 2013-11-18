package es.jtestme.filter;

public enum ParameterType {

    /**
     * Param to indicate the path to configuration file, by default is classpath:jtestme.properties.
     */
    CONFIG_LOCATION("config-location"),

    /**
     * Param to define the encoding of the response, by default is UTF-8.
     */
    ENCODING("encoding"),

    /**
     * Param to define is active logger, by default is true
     */
    LOG("log"),

    /**
     * Param to define de name of the request parameter that
     * represents the type format of the response, by default is "format".
     */
    PARAMETER_FORMAT("param-type-format"),

    /**
     * Param to define the default viewer: HTML, JSON, TXT, XML or CUSTOM, by default the value is HTML
     */
    DEFAULT_VIEWER("default-viewer"),

    /**
     * Param to define the class that implements the HTML viewer, by default is the class
     * es.jtestme.viewers.impl.HTMLViewer
     */
    HTML_VIEWER_CLASS("html-viewer-class"),

    /**
     * Param to define the class that implements the JSON viewer, by default is the class
     * es.jtestme.viewers.impl.JSONViewer
     */
    JSON_VIEWER_CLASS("json-viewer-class"),

    /**
     * Param to define the class that implements the TXT viewer, by default is the class
     * es.jtestme.viewers.impl.PlainTextViewer
     */
    TXT_VIEWER_CLASS("txt-viewer-class"),

    /**
     * Param to define the class that implements the XML viewer, by default is the class
     * es.jtestme.viewers.impl.XMLViewer
     */
    XML_VIEWER_CLASS("xml-viewer-class"),

    /**
     * Param to define the class that implements the CUSTOM viewer, by default is the class
     * es.jtestme.viewers.impl.CustomViewer
     */
    CUSTOM_VIEWER_CLASS("custom-viewer-class"),

    /**
     * Param to define is active schedule, by default is false
     */
    SCHEDULE("schedule"),

    /**
     * Param to define the time in minutes of the period to execute of the scheduler, by default is 10'
     */
    SCHEDULE_PERIOD("schedule-period"),

    /**
     * Param to define the viewer to register the log in case of error, by default is TXT
     */
    SCHEDULE_VIEWER("schedule-viewer");

    private final String parameter;

    private ParameterType(final String parameter) {
        this.parameter = parameter;
    }

    public String getCode() {
        return parameter;
    }

    static ParameterType toParameterType(final String str) {
        ParameterType type = null;
        try {
            type = str != null ? valueOf(str.toUpperCase()) : null;
        } catch (final IllegalArgumentException e) {
            type = null;
        }
        return type;
    }
}
