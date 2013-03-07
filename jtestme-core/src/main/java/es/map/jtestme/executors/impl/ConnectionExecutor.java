package es.map.jtestme.executors.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import es.map.jtestme.domain.JTestMeResult;

public class ConnectionExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_URL = "url";

    public ConnectionExecutor(final Map<String, String> params) {
        super(params);

        HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
            public boolean verify(final String arg0, final SSLSession arg1) {
                return true;
            }
        });
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        final String url = params.get(PARAM_URL);

        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
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
}
