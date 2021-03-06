package es.jtestme.viewers.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import es.jtestme.viewers.Viewer;

public abstract class AbstractViewer implements Viewer {

    /**
     * @return
     */
    protected final String getHostName() {
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
    protected final String getCurrentDateAndTime() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault())
                .format(new Date());
    }
}
