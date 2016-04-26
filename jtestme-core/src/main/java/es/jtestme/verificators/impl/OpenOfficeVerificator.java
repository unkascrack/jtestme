package es.jtestme.verificators.impl;

import java.net.ConnectException;
import java.util.Map;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;

import es.jtestme.domain.VerificatorResult;

public final class OpenOfficeVerificator extends AbstractVerificator {

    private static final int DEFAULT_PORT = 8700;

    private static final String PARAM_HOST = "host";
    private static final String PARAM_PORT = "port";

    private final String host;
    private final Integer port;

    public OpenOfficeVerificator(final Map<String, String> params) {
        super(params);
        this.host = getParamString(PARAM_HOST, "localhost");
        this.port = getParamInteger(PARAM_PORT, DEFAULT_PORT);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();
        OpenOfficeConnection connection = null;
        try {
            connection = new SocketOpenOfficeConnection(this.host, this.port);
            connection.connect();
            result.setSuccess(true);
        } catch (final ConnectException e) {
            result.setCause(e);
        } catch (final Throwable e) {
            result.setCause(e);
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
