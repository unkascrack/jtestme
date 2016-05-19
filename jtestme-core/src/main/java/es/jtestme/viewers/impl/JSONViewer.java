package es.jtestme.viewers.impl;

import java.util.List;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.filter.Parameters;

public final class JSONViewer extends AbstractViewer {

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
                builder.append(TAB).append(TAB).append(printProperty("type", result.getType())).append(",")
                        .append(NEW_LINE);
                builder.append(TAB).append(TAB).append(printProperty("name", result.getName())).append(",")
                        .append(NEW_LINE);
                builder.append(TAB).append(TAB).append(printProperty("description", result.getDescription()))
                        .append(",").append(NEW_LINE);
                builder.append(TAB).append(TAB).append(printProperty("success", result.getSuccessString()));
                if (result.isSuccess()) {
                    builder.append(NEW_LINE);
                } else {
                    builder.append(",").append(NEW_LINE);
                    builder.append(TAB).append(TAB).append(printProperty("message", result.getMessage())).append(",")
                            .append(NEW_LINE);
                    builder.append(TAB).append(TAB).append(printProperty("resolution", result.getResolution()))
                            .append(",").append(NEW_LINE);
                    builder.append(TAB).append(TAB).append(printProperty("cause", result.getCauseString()))
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
        builder.append(printProperty("version", Parameters.getjTestMeVersion())).append(",").append(NEW_LINE);
        builder.append(printProperty("hostname", getHostName())).append(",").append(NEW_LINE);
        builder.append("\"verificators\":{").append(NEW_LINE);
        return builder.toString();
    }

    private String footer() {
        final StringBuilder builder = new StringBuilder();
        builder.append("}").append(NEW_LINE);
        return builder.toString();
    }

    private String printProperty(final String name, final String value) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\"").append(name).append("\":");
        builder.append("\"").append(value != null ? value.trim() : "").append("\"");
        return builder.toString();
    }
}
