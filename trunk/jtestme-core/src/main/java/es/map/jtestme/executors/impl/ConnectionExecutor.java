package es.map.jtestme.executors.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import es.map.jtestme.domain.JTestMeResult;
import es.map.jtestme.logger.JTestMeLogger;

public class ConnectionExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_URL = "url";

    public ConnectionExecutor(final Map<String, String> params) {
        super(params);
        fixHttpsConnections();
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        final String url = params.get(PARAM_URL);

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            final int responseCode = connection.getResponseCode();
            if (200 >= responseCode && responseCode <= 399) {
                result.setSuscess(true);
            } else {
                result.setMessage(connection.getResponseMessage());
            }
        } catch (final MalformedURLException e) {
            result.setMessage(e.toString());
        } catch (final IOException e) {
            result.setMessage(e.toString());
        } catch (final Throwable e) {
            result.setMessage(e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * 
     */
    private void fixHttpsConnections() {
        try {
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
            JTestMeLogger.warn("JTestMeExecutor.ConnectionExecutor failed: " + e.getMessage());
        }
    }
}
