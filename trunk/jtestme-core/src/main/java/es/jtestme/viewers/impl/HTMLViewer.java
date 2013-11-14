package es.jtestme.viewers.impl;

import java.util.List;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.filter.Parameters;

public class HTMLViewer extends AbstractViewer {

    public String getContentType() {
        return "text/html";
    }

    public String getContentViewer(final List<VerificatorResult> results) {
        final StringBuilder builder = new StringBuilder();
        builder.append(header());
        if (results == null || results.isEmpty()) {
            builder.append("<img src='?resource=img/error.png' alt='Error'/>");
            builder.append("<b>&nbsp;No se han definidio ningún test!!!</b>");
        } else {
            int contador = 1;
            builder.append("<table border='1' cellspacing='1' cellpading='1' width='100%'>").append(NEW_LINE);
            for (final VerificatorResult result : results) {
                builder.append("<tr>").append(NEW_LINE);
                builder.append("<td align='right' style='white-space:nowrap' valign='top'>").append(NEW_LINE);
                builder.append("<b>").append(result.getName()).append("</b>").append(NEW_LINE);
                builder.append("</td>").append(NEW_LINE);
                builder.append("<td align='center' valign='top'>").append(NEW_LINE);
                if (result.isSuccess()) {
                    builder.append("<img src='?resource=img/success.png' alt='Success' style='padding:4px'/>");
                } else {
                    builder.append("<img src='?resource=img/error.png' alt='Error' style='padding:4px'/>");
                }
                builder.append("</td>").append(NEW_LINE);
                builder.append("<td align='left' width='100%' valign='top'>").append(NEW_LINE);
                if (result.isSuccess()) {
                    builder.append("<b>").append(result.getSuccessString()).append("</b>").append(NEW_LINE);
                } else {
                    if (result.getResolution() != null) {
                        builder.append("<em>").append(result.getResolution()).append("</em><br/>").append(NEW_LINE);
                    }
                    if (result.getCause() != null) {
                        builder.append("<a href='javascript:showHide(").append(contador).append(")'>");
                        builder.append("<img src='?resource=img/plus.png' id='").append(contador).append("Img'/>");
                        builder.append("</a>").append(NEW_LINE);
                    }
                    builder.append("<b>").append(result.getMessage()).append("</b>").append(NEW_LINE);
                    if (result.getCause() != null) {
                        builder.append("<div id='").append(contador).append("' style='display:none'>").append(NEW_LINE);
                        builder.append(getStackTraceToHTML(result.getCauseString())).append(NEW_LINE);
                        builder.append("</div>").append(NEW_LINE);
                    }
                }
                builder.append("</td>").append(NEW_LINE);
                builder.append("</tr>").append(NEW_LINE);
                contador++;
            }
            builder.append("</table>").append(NEW_LINE);
        }
        builder.append(footer());
        return builder.toString();
    }

    private String getStackTraceToHTML(final String stackTrace) {
        final StringBuilder builder = new StringBuilder();
        if (stackTrace != null && stackTrace.trim().length() > 0) {
            builder.append("<blockquote>").append(NEW_LINE);
            builder.append(stackTrace.replace(NEW_LINE, "<br/>"));
            builder.append("</blockquote>").append(NEW_LINE);
        }
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
        builder.append("<title>").append("JTestMe Monitor ").append(Parameters.getjTestMeVersion()).append("</title>")
                .append(NEW_LINE);
        builder.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>").append(NEW_LINE);
        builder.append("<meta name='product' content='JTestMe'/>").append(NEW_LINE);
        builder.append("<meta name='version' content='").append(Parameters.getjTestMeVersion()).append("'/>")
                .append(NEW_LINE);
        builder.append("<link rel='stylesheet' href='?resource=css/jtestme.css' type='text/css'/>").append(NEW_LINE);
        builder.append("</head>").append(NEW_LINE);
        builder.append("<body>").append(NEW_LINE);
        builder.append("<h2>").append("<a href='http://jtestme.googlecode.com/' target='_blank'>JTestMe</a> Monitor ")
                .append(Parameters.getjTestMeVersion()).append(":</h2>").append(NEW_LINE);
        builder.append("<hr/>");
        builder.append("<p><a href='?' title='Refresh'><img src='?resource=img/refresh.png' alt='Refresh'/></a>");
        builder.append("&nbsp;Monitoring taken at ").append(currentDateAndTime).append(" on ").append(hostName)
                .append(":</p>").append(NEW_LINE);
        return builder.toString();
    }

    private String footer() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<script type='text/javascript' src='?resource=js/jtestme.js'></script>").append(NEW_LINE);
        builder.append("</body>").append(NEW_LINE);
        builder.append("</html>").append(NEW_LINE);
        return builder.toString();
    }
}