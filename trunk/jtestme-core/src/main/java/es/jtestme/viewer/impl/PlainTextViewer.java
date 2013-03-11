package es.jtestme.viewer.impl;

import java.util.List;

import es.jtestme.domain.JTestMeResult;

public class PlainTextViewer extends JTestMeDefaultViewer {

    public String getExtension() {
        return ".txt";
    }

    public String getContentType() {
        return "text/plain";
    }

    public String getContentViewer(final List<JTestMeResult> results) {
        final StringBuilder builder = new StringBuilder();
        builder.append(header());
        if (results == null || results.isEmpty()) {
            builder.append("No se han definido tests.").append(NEW_LINE);
        } else {
            for (final JTestMeResult result : results) {
                builder.append(TAB).append(" * ").append(result.toString()).append(NEW_LINE);
            }
        }
        builder.append(footer());
        return builder.toString();
    }

    private String header() {
        final StringBuilder builder = new StringBuilder();
        builder.append("JTestMe Monitoring on host ").append(getHostName()).append(" at ")
                .append(getCurrentDateAndTime()).append(".").append(NEW_LINE);
        return builder.toString();
    }

    private String footer() {
        final StringBuilder builder = new StringBuilder();
        return builder.toString();
    }

}
