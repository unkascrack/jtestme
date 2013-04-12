package es.jtestme.verificators.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

import es.jtestme.domain.VerificatorResult;

public class ConnectionVerificator extends AbstractVerificator {

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
        url = getParamString(PARAM_URL);
        timeout = getParamInteger(PARAM_TIMEOUT, 5000);
        trustStore = getParamString(PARAM_TRUSTSTORE);
        trustStorePassword = getParamString(PARAM_TRUSTSTOREPASSWORD);
        final String proxyHost = getParamString(PARAM_PROXY_HOST);
        final Integer proxyPort = getParamInteger(PARAM_PROXY_PORT, null);
        if (proxyHost != null && proxyPort != null) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        } else {
            proxy = Proxy.NO_PROXY;
        }
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();

        loadTrustStore(trustStore, trustStorePassword);

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection(proxy);
            connection.setConnectTimeout(timeout);
            final int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode <= 399) {
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
            relaseTrustStore(trustStore, trustStorePassword);
        }
        return result;
    }
}
