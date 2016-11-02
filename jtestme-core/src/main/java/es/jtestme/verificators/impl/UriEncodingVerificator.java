package es.jtestme.verificators.impl;

import java.lang.management.ManagementFactory;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.catalina.Server;

import es.jtestme.domain.VerificatorResult;

public final class UriEncodingVerificator extends AbstractVerificator {

    private static final String PARAM_ENCODING = "encoding";

    private final String encoding;

    public UriEncodingVerificator(final Map<String, String> params) {
        super(params);
        this.encoding = getParamString(PARAM_ENCODING);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();
        if (this.encoding == null) {
            result.setMessage(getClass().getSimpleName() + ": no se ha definido la codificación URI para verificar.");
            return result;
        }

        try {
            final Server server = findServer();
            validateServer(server);
            final String uriEncoding = server.findServices()[0].findConnectors()[0].getURIEncoding();
            if (this.encoding.equalsIgnoreCase(uriEncoding)) {
                result.setSuccess(true);
            } else {
                result.setMessage(String.format("URI Encoding incorrecta = %s.", this.encoding));
            }
        } catch (final Throwable e) {
            result.setCause(e);
        }
        return result;
    }

    private Server findServer() {
        final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Server server = null;
        try {
            final ObjectName objectName = new ObjectName("Catalina", "type", "Server");
            server = (Server) mBeanServer.getAttribute(objectName, "managedResource");
        } catch (final Throwable e) {
        }

        if (server == null) {
            try {
                final ObjectName name = new ObjectName("Tomcat", "type", "Server");
                server = (Server) mBeanServer.getAttribute(name, "managedResource");
            } catch (final Throwable e) {
            }
        }
        return server;
    }

    private void validateServer(final Server server) throws RuntimeException {
        if (server == null) {
            throw new RuntimeException("No se ha podido recuperar el ServerEngine del servidor.");
        }
        if (server.findServices() == null && server.findServices().length == 0) {
            throw new RuntimeException("No se ha podido recuperar el ServiceEngine del servidor.");
        }
        if (server.findServices()[0].findConnectors() == null
                || server.findServices()[0].findConnectors().length == 0) {
            throw new RuntimeException("No se ha podido recuperar el ConnectorEngine del servidor.");
        }
    }
}
