package es.jtestme.test.viewers;

import java.util.List;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.viewers.impl.AbstractViewer;

public class MyXMLViewer extends AbstractViewer {

    public String getContentType() {
        return "text/xml";
    }

    public String getContentViewer(final List<VerificatorResult> results) {
        final StringBuilder builder = new StringBuilder();
        builder.append(header());
        if (results != null && !results.isEmpty()) {
            for (final VerificatorResult result : results) {
                builder.append("<servicio>").append(NEW_LINE);
                builder.append("<nombre>").append(result.getName()).append("</nombre>").append(NEW_LINE);
                builder.append("<estado>").append(result.isSuscess() ? "OK": "KO").append("</estado>").append(NEW_LINE);
                if (!result.isSuscess()) {
                    builder.append("<descError>").append(result.getMessage()).append("</descError>").append(NEW_LINE);
                    builder.append("<accionError>").append(result.getResolution()).append("</accionError>")
                            .append(NEW_LINE);
                }
                builder.append("</servicio>").append(NEW_LINE);
            }
        }
        builder.append(footer());
        return builder.toString();
    }

    private String header() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<?xml version='1.0' encoding='ISO-8859-15'?>").append(NEW_LINE);
        builder.append("<verificarSistema nodo='").append(getHostName()).append("'>").append(NEW_LINE);
        return builder.toString();
    }

    private String footer() {
        final StringBuilder builder = new StringBuilder();
        builder.append("</verificarSistema>").append(NEW_LINE);
        return builder.toString();
    }
}
