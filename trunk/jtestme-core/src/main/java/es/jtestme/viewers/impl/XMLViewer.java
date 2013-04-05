package es.jtestme.viewers.impl;

import java.util.List;

import es.jtestme.domain.VerificatorResult;

public class XMLViewer extends AbstractViewer {

    public String getContentType() {
        return "text/xml";
    }

    public String getContentViewer(final List<VerificatorResult> results) {
        final StringBuilder builder = new StringBuilder();
        builder.append(header());
        if (results != null && !results.isEmpty()) {
            for (final VerificatorResult result : results) {
                builder.append("<verificator>").append(NEW_LINE);
                builder.append("<type>").append(result.getType()).append("</type>").append(NEW_LINE);
                builder.append("<name>").append(result.getName()).append("</name>").append(NEW_LINE);
                builder.append("<description>").append(result.getDescription()).append("</description>")
                        .append(NEW_LINE);
                builder.append("<success>").append(result.getSuccessString()).append("</success>").append(NEW_LINE);
                if (!result.isSuccess()) {
                    builder.append("<message>").append(result.getMessage()).append("</message>").append(NEW_LINE);
                    builder.append("<cause>").append(result.getCauseString()).append("</cause>").append(NEW_LINE);
                    builder.append("<resolution>").append(result.getResolution()).append("</resolution>")
                            .append(NEW_LINE);
                }
                builder.append("</verificator>").append(NEW_LINE);
            }
        }
        builder.append(footer());
        return builder.toString();
    }

    private String header() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<?xml version='1.0' encoding='UTF-8'?>").append(NEW_LINE);
        builder.append("<verificators>").append(NEW_LINE);
        return builder.toString();
    }

    private String footer() {
        final StringBuilder builder = new StringBuilder();
        builder.append("</verificators>").append(NEW_LINE);
        return builder.toString();
    }
}
