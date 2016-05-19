package es.jtestme.viewers.impl;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.Iterator;
import java.util.List;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.filter.Parameters;

public final class HTMLViewer extends AbstractViewer {

    public String getContentType() {
        return "text/html";
    }

    public String getContentViewer(final List<VerificatorResult> results) {
        final StringBuilder builder = new StringBuilder();
        builder.append(header());
        if (results == null || results.isEmpty()) {
            builder.append("<img src='?resource=img/error.png' alt='Error'></img>");
            builder.append("<b>&nbsp;No se han definidio ningún test!!!</b>");
        } else {
            int contador = 1;
            builder.append("<table border='1' cellspacing='1' cellpading='1' width='100%'>");
            for (final VerificatorResult result : results) {
                builder.append("<tr>");
                builder.append("<td align='right' style='white-space:nowrap' valign='top'>");
                builder.append("<b>").append(result.getName()).append("</b>");
                builder.append("</td>");
                builder.append("<td align='center' valign='top'>");
                if (result.isSuccess()) {
                    builder.append("<img src='?resource=img/success.png' alt='Success' style='padding:4px'></img>");
                } else {
                    builder.append("<img src='?resource=img/error.png' alt='Error' style='padding:4px'></img>");
                }
                builder.append("</td>");
                builder.append("<td align='left' width='100%' valign='top'>");
                if (result.isSuccess()) {
                    builder.append("<b>").append(result.getSuccessString()).append("</b>");
                } else {
                    if (result.getResolution() != null) {
                        builder.append("<em>").append(result.getResolution()).append("</em><br/>");
                    }
                    if (result.getCause() != null) {
                        builder.append("<a href='javascript:showHide(").append(contador).append(")'>");
                        builder.append("<img src='?resource=img/plus.png' id='").append(contador).append("Img'></img>");
                        builder.append("</a>");
                    }
                    builder.append("<b>").append(result.getMessage()).append("</b>");
                    if (result.getCause() != null) {
                        builder.append("<div id='").append(contador).append("' style='display:none'>");
                        builder.append(getStackTraceToHTML(result.getCauseString()));
                        builder.append("</div>");
                    }
                }
                builder.append("</td>");
                builder.append("</tr>");
                contador++;
            }
            builder.append("</table>");
        }
        builder.append(footer());
        return builder.toString();
    }

    private String getStackTraceToHTML(final String stackTrace) {
        final StringBuilder builder = new StringBuilder();
        if (stackTrace != null && stackTrace.trim().length() > 0) {
            builder.append("<blockquote>");
            builder.append(stackTrace.replace(NEW_LINE, "<br/>"));
            builder.append("</blockquote>");
        }
        return builder.toString();
    }

    private String header() {
        final String hostName = getHostName();
        final String currentDateAndTime = getCurrentDateAndTime();

        final StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>");
        builder.append("<html>");
        builder.append("<head>");
        builder.append("<title>").append("JTestMe Monitor ").append(Parameters.getjTestMeVersion()).append("</title>");
        builder.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>");
        builder.append("<meta name='product' content='JTestMe'/>");
        builder.append("<meta name='version' content='").append(Parameters.getjTestMeVersion()).append("'/>");
        builder.append("<link rel='stylesheet' href='?resource=css/jtestme.css' type='text/css'/>");
        builder.append("</head>");
        builder.append("<body>");
        builder.append("<h2>")
                .append("<a href='https://github.com/unkascrack/jtestme' target='_blank'>JTestMe</a> Monitor ")
                .append(Parameters.getjTestMeVersion()).append(":</h2>");
        builder.append("<hr/>");
        builder.append("<p><a href='?' title='Refresh'><img src='?resource=img/refresh.png' alt='Refresh'></img></a>");
        builder.append("&nbsp;Monitoring taken at ").append(currentDateAndTime).append(" on ").append(hostName)
                .append(":</p>");
        return builder.toString();
    }

    private static final int MB = 1024 * 1024;

    private String footer() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<br/>");
        builder.append("<div>");
        builder.append("<img src='?resource=img/memory.png' alt='Memory'></img>");
        builder.append("<b>Memory Statistics:</b>");
        builder.append("<ul>");
        final Iterator<MemoryPoolMXBean> iter = ManagementFactory.getMemoryPoolMXBeans().iterator();
        while (iter.hasNext()) {
            final MemoryPoolMXBean item = iter.next();
            builder.append("<li>");
            builder.append(item.getName());
            builder.append(" (").append(item.getType()).append("): ");
            builder.append(item.getUsage().getMax() / MB).append(" MB");
            builder.append("</li>");
        }
        builder.append("</ul>");
        builder.append("</div>");
        builder.append("<script type='text/javascript' src='?resource=js/jtestme.js'></script>");
        builder.append("</body>");
        builder.append("</html>");
        return builder.toString();
    }
}
