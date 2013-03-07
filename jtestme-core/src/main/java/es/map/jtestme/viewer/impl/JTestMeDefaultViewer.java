package es.map.jtestme.viewer.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import es.map.jtestme.viewer.JTestMeViewer;

public abstract class JTestMeDefaultViewer implements JTestMeViewer {

    protected static Logger logger = Logger.getAnonymousLogger();

    /**
     * @return
     */
    protected String getHostName() {
        String hostName = "unknown";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (final UnknownHostException e) {
            logger.warning("HTMLViewer could not load hostName: " + e.getMessage());
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
