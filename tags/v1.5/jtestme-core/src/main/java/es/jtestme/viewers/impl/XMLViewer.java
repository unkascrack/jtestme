package es.jtestme.viewers.impl;

import java.util.List;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.filter.Parameters;

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
                builder.append(printElement("type", result.getType()));
                builder.append(printElement("name", result.getName()));
                builder.append(printElement("description", result.getDescription()));
                builder.append(printElement("success", result.getSuccessString()));
                if (!result.isSuccess()) {
                    builder.append(printElement("message", result.getMessage()));
                    builder.append(printElement("cause", result.getCauseString()));
                    builder.append(printElement("resolution", result.getResolution()));
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
        builder.append("<jtestme>").append(NEW_LINE);
        builder.append(printElement("version", Parameters.getjTestMeVersion()));
        builder.append(printElement("hostname", getHostName()));
        builder.append("<verificators>").append(NEW_LINE);
        return builder.toString();
    }

    private String footer() {
        final StringBuilder builder = new StringBuilder();
        builder.append("</verificators>").append(NEW_LINE);
        builder.append("</jtestme>").append(NEW_LINE);
        return builder.toString();
    }

    private String printElement(final String element, final String value) {
        final StringBuilder builder = new StringBuilder();
        if (value != null && value.trim().length() > 0) {
            builder.append("<").append(element).append(">");
            builder.append("<![CDATA[").append(value.trim()).append("]]>");
            builder.append("</").append(element).append(">");
            builder.append(NEW_LINE);
        }
        return builder.toString();
    }
}
