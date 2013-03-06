package es.map.jtestme.executors.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import es.map.jtestme.domain.JTestMeResult;

public class ConnectionExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_URL = "url";

    public ConnectionExecutor(final Map<String, String> params) {
        super(params);
    }

    public JTestMeResult executor() {
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
            result.setMessage(e.getMessage());
        } catch (final IOException e) {
            result.setMessage(e.getMessage());
        } catch (final Throwable e) {
            result.setMessage(e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

}
