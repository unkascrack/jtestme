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
    private static final String PARAM_TRUSTSTORE = "truststore";
    private static final String PARAM_TRUSTSTOREPASSWORD = "truststorepassword";

    private String url;
    private String trustStore;
    private String defaultTrustStore;
    private String trustStorePassword;
    private String defaultTrustStorePassword;

    public ConnectionExecutor(final Map<String, String> params) {
        super(params);
        fixHttpsConnections();
        if (params != null) {
            url = params.get(PARAM_URL);
            trustStore = params.get(PARAM_TRUSTSTORE);
            trustStorePassword = params.get(PARAM_TRUSTSTOREPASSWORD);
        }

    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        loadTrustStore();

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
            relaseTrustStore();
        }
        return result;
    }

    private void loadTrustStore() {
        if (trustStore != null && trustStore.trim().length() > 0) {
            defaultTrustStore = System.getProperty("javax.net.ssl.trustStore");
            System.setProperty("javax.net.ssl.trustStore", trustStore);
        }

        if (trustStorePassword != null && trustStorePassword.trim().length() > 0) {
            defaultTrustStorePassword = System.getProperty("javax.net.ssl.trustStorePassword");
            System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
        }
    }

    private void relaseTrustStore() {
        if (trustStore != null && trustStore.trim().length() > 0) {
            if (defaultTrustStore != null && defaultTrustStore.trim().length() > 0) {
                System.setProperty("javax.net.ssl.trustStore", defaultTrustStore);
            } else {
                System.clearProperty("javax.net.ssl.trustStore");
            }
        }

        if (trustStorePassword != null && trustStorePassword.trim().length() > 0) {
            if (defaultTrustStorePassword != null && defaultTrustStorePassword.trim().length() > 0) {
                System.setProperty("javax.net.ssl.trustStorePassword", defaultTrustStorePassword);
            } else {
                System.clearProperty("javax.net.ssl.trustStorePassword");
            }
        }
    }

    /**
     * 
     */
    private void fixHttpsConnections() {
        try {
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
            JTestMeLogger.warn("JTestMeExecutor.ConnectionExecutor failed: " + e.getMessage());
        }
    }
}
