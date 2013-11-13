package es.jtestme.viewers.impl;

import java.util.List;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.filter.Parameters;

public class JSONViewer extends AbstractViewer {

    public String getContentType() {
        return "application/json";
    }

    public String getContentViewer(final List<VerificatorResult> results) {
        final StringBuilder builder = new StringBuilder();
        builder.append(header());
        if (results != null && !results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
                final VerificatorResult result = results.get(i);
                builder.append(TAB).append("\"verificator\":{").append(NEW_LINE);
                builder.append(TAB).append(TAB).append("\"type\":\"").append(result.getType()).append("\",")
                        .append(NEW_LINE);
                builder.append(TAB).append(TAB).append("\"name\":\"").append(result.getName()).append("\",")
                        .append(NEW_LINE);
                builder.append(TAB).append(TAB).append("\"description\":\"").append(result.getDescription())
                        .append("\",").append(NEW_LINE);
                builder.append(TAB).append(TAB).append("\"success\":\"").append(result.getSuccessString()).append("\"");
                if (result.isSuccess()) {
                    builder.append(NEW_LINE);
                } else {
                    builder.append(",").append(NEW_LINE);
                    builder.append(TAB).append(TAB).append("\"message\":\"").append(result.getMessage()).append("\",")
                            .append(NEW_LINE);
                    builder.append(TAB).append(TAB).append("\"resolution\":\"").append(result.getResolution())
                            .append("\",").append(NEW_LINE);
                    builder.append(TAB).append(TAB).append("\"cause\":\"").append(result.getCauseString()).append("\"")
                            .append(NEW_LINE);
                }
                if (i == results.size() - 1) {
                    builder.append(TAB).append("}").append(NEW_LINE);
                } else {
                    builder.append(TAB).append("},").append(NEW_LINE);
                }
            }
        }
        builder.append(footer());
        return builder.toString();
    }

    private String header() {
        final StringBuilder builder = new StringBuilder();
        builder.append("{").append(NEW_LINE);
        builder.append("\"version\":\"").append(Parameters.getjTestMeVersion()).append("\",").append(NEW_LINE);
        builder.append("\"hostname\":\"").append(getHostName()).append("\",").append(NEW_LINE);
        builder.append("\"verificators\":{").append(NEW_LINE);
        return builder.toString();
    }

    private String footer() {
        final StringBuilder builder = new StringBuilder();
        builder.append("}").append(NEW_LINE);
        return builder.toString();
    }
}
