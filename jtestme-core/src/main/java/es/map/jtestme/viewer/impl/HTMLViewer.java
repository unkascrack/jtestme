package es.map.jtestme.viewer.impl;

import java.util.List;

import es.map.jtestme.domain.JTestMeResult;

public class HTMLViewer extends JTestMeDefaultViewer {

    public String getExtension() {
        return ".xhtml";
    }

    public String getContentType() {
        return "text/html";
    }

    public String getContentViewer(final List<JTestMeResult> results) {
        final StringBuilder builder = new StringBuilder();
        builder.append(header());
        if (results == null || results.isEmpty()) {
            // TODO: Añadir README
            builder.append("ICON ERROR");
            builder.append("No se han definidio ningún test!!! README");
        } else {
            builder.append("<table border='1' cellspacing='0' cellpading='0'>").append(NEW_LINE);
            for (final JTestMeResult result : results) {
                builder.append("<tr>").append(NEW_LINE);
                builder.append("<td align='right'>").append(NEW_LINE);
                builder.append("<b>").append(result.getName()).append("</b>").append(NEW_LINE);
                builder.append("</td>").append(NEW_LINE);
                builder.append("<td align='center'>").append(NEW_LINE);
                if (result.isSuscess()) {
                    builder.append("<img src='?resource=success.png' alt='Suscess'/>");
                } else {
                    builder.append("<img src='?resource=error.png' alt='Error'/>");
                }
                builder.append("<b>").append(result.getSuscessString()).append("</b>").append(NEW_LINE);
                builder.append("</td>").append(NEW_LINE);
                builder.append("<td align='left'>").append(NEW_LINE);
                if (!result.isSuscess()) {
                    builder.append("<b>").append(result.getMessage()).append("</b>").append(NEW_LINE);
                    builder.append("<br/><em>").append(result.getResolution()).append("</em>").append(NEW_LINE);
                }
                builder.append("</td>").append(NEW_LINE);
                builder.append("</tr>").append(NEW_LINE);
            }
            builder.append("</table>").append(NEW_LINE);
        }
        builder.append(footer());
        return builder.toString();
    }

    private String header() {
        final String hostName = getHostName();
        final String currentDateAndTime = getCurrentDateAndTime();

        final StringBuilder builder = new StringBuilder();
        builder.append(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
                .append(NEW_LINE);
        builder.append("<html>").append(NEW_LINE);
        builder.append("<head>").append(NEW_LINE);
        builder.append("<title>").append("JTestMe Monitoring").append("</title>").append(NEW_LINE);
        builder.append("</head>").append(NEW_LINE);
        builder.append("<body>").append(NEW_LINE);
        builder.append("<h1>").append("JTestMe Monitoring:").append("</h1>").append(NEW_LINE);
        builder.append("<hr/>");
        builder.append("<a href='?' title='Refresh'><img src='?resource=refresh.png' alt='Refresh'/></a>");
        builder.append("Monitoring taken at ").append(currentDateAndTime).append(" on ").append(hostName).append(".")
                .append(NEW_LINE);
        return builder.toString();
    }

    private String footer() {
        final StringBuilder builder = new StringBuilder();
        builder.append("</body>").append(NEW_LINE);
        builder.append("</html>").append(NEW_LINE);
        return builder.toString();
    }
}
