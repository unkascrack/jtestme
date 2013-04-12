package es.jtestme.test.viewers;

import java.util.List;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.viewers.impl.AbstractViewer;

public class MyJSONViewer extends AbstractViewer {

    public String getContentType() {
        return "application/json";
    }

    public String getContentViewer(final List<VerificatorResult> results) {
        final StringBuilder builder = new StringBuilder();
        builder.append(header());
        if (results != null && !results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
                final VerificatorResult result = results.get(i);
                builder.append(TAB).append("\"servicio\" : {").append(NEW_LINE);
                builder.append(TAB).append(TAB).append("\"nombre\" : \"").append(result.getName()).append("\"")
                        .append(NEW_LINE);
                builder.append(TAB).append(TAB).append("\"estado\" : \"").append(result.getSuccessString())
                        .append("\"").append(NEW_LINE);
                if (!result.isSuccess()) {
                    builder.append(TAB).append(TAB).append("\"descError\" : \"").append(result.getMessage())
                            .append("\"").append(NEW_LINE);
                    builder.append(TAB).append(TAB).append("\"accionError\" : \"").append(result.getResolution())
                            .append("\"").append(NEW_LINE);
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
        builder.append("\"verificarSistema\" : {").append(NEW_LINE);
        return builder.toString();
    }

    private String footer() {
        final StringBuilder builder = new StringBuilder();
        builder.append("}").append(NEW_LINE);
        return builder.toString();
    }
}
