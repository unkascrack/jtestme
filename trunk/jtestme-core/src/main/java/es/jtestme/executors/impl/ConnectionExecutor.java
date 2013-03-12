package es.jtestme.executors.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.logger.JTestMeLogger;

public class ConnectionExecutor extends JTestMeDefaultExecutor {

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

    public ConnectionExecutor(final Map<String, String> params) {
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
        fixHttpsConnections();
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        loadTrustStore(trustStore, trustStorePassword);

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection(proxy);
            connection.setConnectTimeout(timeout);
            final int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode <= 399) {
                result.setSuscess(true);
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

    /**
     * 
     */
    private void fixHttpsConnections() {
        try {
            HttpURLConnection.setFollowRedirects(false);

            // System.setProperty("jsse.enableSNIExtension", "false");
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
                }

                public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
                }
            } };

            final SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            final HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (final Throwable e) {
            JTestMeLogger.warn("JTestMeExecutor.ConnectionExecutor failed: " + e.getMessage(), e);
        }
    }
}
