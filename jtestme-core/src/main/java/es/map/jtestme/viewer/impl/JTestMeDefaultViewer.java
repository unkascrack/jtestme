package es.map.jtestme.viewer.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import es.map.jtestme.viewer.JTestMeViewer;

public abstract class JTestMeDefaultViewer implements JTestMeViewer {

    /**
     * @return
     */
    protected String getHostName() {
        String hostName = "unknown";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (final UnknownHostException e) {
            hostName = "unknown";
        }
        return hostName;
    }

    /**
     * @return
     */
    protected String getCurrentDateAndTime() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault()).format(
                new Date());
    }
}
