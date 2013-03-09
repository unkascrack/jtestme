package es.map.jtestme.executors.impl;

import java.net.ConnectException;
import java.util.Map;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;

import es.map.jtestme.domain.JTestMeResult;

public class OpenOfficeExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_HOST = "host";
    private static final String PARAM_PORT = "port";

    private String host;
    private Integer port;

    public OpenOfficeExecutor(final Map<String, String> params) {
        super(params);
        if (params != null) {
            host = toString(params.get(PARAM_HOST), "localhost");
            port = toInteger(params.get(PARAM_PORT), 8700);
        }
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();
        OpenOfficeConnection connection = null;
        try {
            connection = new SocketOpenOfficeConnection(host, port);
            connection.connect();
        } catch (final ConnectException e) {
            result.setMessage(e.toString());
        } catch (final Throwable e) {
            result.setMessage(e.toString());
        } finally {
            if (connection != null && connection.isConnected()) {
                try {
                    connection.disconnect();
                } catch (final Throwable e) {
                }
            }
        }
        return result;
    }

}
