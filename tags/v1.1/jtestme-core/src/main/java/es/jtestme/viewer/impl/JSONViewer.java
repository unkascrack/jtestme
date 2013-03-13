package es.jtestme.viewer.impl;

import java.util.List;

import es.jtestme.domain.JTestMeResult;

public class JSONViewer extends JTestMeDefaultViewer {

    public String getExtension() {
        return ".json";
    }

    public String getContentType() {
        return "application/json";
    }

    public String getContentViewer(final List<JTestMeResult> results) {
        final StringBuilder builder = new StringBuilder();
        builder.append(header());
        if (results != null && !results.isEmpty()) {
            for (final JTestMeResult result : results) {
                builder.append("\"servicio\" : {").append(NEW_LINE);
                builder.append("\"nombre\" : \"").append(result.getName()).append("\"").append(NEW_LINE);
                builder.append("\"estado\" : \"").append(result.getSuscessString()).append("\"").append(NEW_LINE);
                if (!result.isSuscess()) {
                    builder.append("\"descError\" : \"").append(result.getMessage()).append("\"").append(NEW_LINE);
                    builder.append("\"accionError\" : \"").append(result.getResolution()).append("\"").append(NEW_LINE);
                }
                builder.append("}").append(NEW_LINE);
            }
        }
        builder.append(footer());
        return builder.toString();
    }

    private String header() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\"verificarSistema\" : {").append(NEW_LINE);
        return builder.toString();
    }

    private String footer() {
        final StringBuilder builder = new StringBuilder();
        builder.append("}").append(NEW_LINE);
        return builder.toString();
    }
}
