package es.jtestme.verificators.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

import es.jtestme.domain.VerificatorResult;

public final class ConnectionVerificator extends AbstractVerificator {

    private static final int DEFAULT_TIMEOUT = 5000;

    private static final String PARAM_URL = "url";
    private static final String PARAM_TIMEOUT = "timeout";
    private static final String PARAM_PROXY_HOST = "proxyhost";
    private static final String PARAM_PROXY_PORT = "proxyport";
    private static final String PARAM_TRUSTSTORE = "truststore";
    private static final String PARAM_TRUSTSTOREPASSWORD = "truststorepassword";

    private final String url;
    private final int timeout;
    private final String trustStore;
    private final String trustStorePassword;
    private Proxy proxy;

    public ConnectionVerificator(final Map<String, String> params) {
        super(params);
        this.url = getParamString(PARAM_URL);
        this.timeout = getParamInteger(PARAM_TIMEOUT, DEFAULT_TIMEOUT);
        this.trustStore = getParamString(PARAM_TRUSTSTORE);
        this.trustStorePassword = getParamString(PARAM_TRUSTSTOREPASSWORD);
        final String proxyHost = getParamString(PARAM_PROXY_HOST);
        final Integer proxyPort = getParamInteger(PARAM_PROXY_PORT, null);
        if (proxyHost != null && proxyPort != null) {
            this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        } else {
            this.proxy = Proxy.NO_PROXY;
        }
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();

        loadTrustStore(this.trustStore, this.trustStorePassword);

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(this.url).openConnection(this.proxy);
            connection.setConnectTimeout(this.timeout);
            final int responseCode = connection.getResponseCode();
            if (responseCode >= HttpURLConnection.HTTP_OK && responseCode <= HttpURLConnection.HTTP_PARTIAL) {
                result.setSuccess(true);
            } else {
                result.setMessage(connection.getResponseMessage());
            }
        } catch (final MalformedURLException e) {
            result.setCause(e);
        } catch (final IOException e) {
            result.setCause(e);
        } catch (final Throwable e) {
            result.setCause(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            relaseTrustStore(this.trustStore, this.trustStorePassword);
        }
        return result;
    }
}
