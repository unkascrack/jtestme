package es.map.jtestme.executors.impl;

import java.util.Map;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import es.map.jtestme.domain.JTestMeResult;

public class SMTPExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_HOST = "host";
    private static final String PARAM_PORT = "port";
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_AUTH = "auth";
    private static final String PARAM_STARTTLS = "starttls";
    private static final String PARAM_STARTSSL = "startssl";

    private String host;
    private Integer port;
    private String username;
    private String password;
    private boolean auth = false;
    private boolean starttls = false;
    private boolean startssl = false;

    public SMTPExecutor(final Map<String, String> params) {
        super(params);
        if (params != null) {
            host = params.get(PARAM_HOST);
            port = toInteger(params.get(PARAM_PORT), 25);
            username = params.get(PARAM_USERNAME);
            password = params.get(PARAM_PASSWORD);
            auth = toBoolean(params.get(PARAM_AUTH), false);
            starttls = toBoolean(params.get(PARAM_STARTTLS), false);
            startssl = toBoolean(params.get(PARAM_STARTSSL), false);
        }
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        Session session = null;
        Transport transport = null;
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", auth);
            props.put("mail.smtp.starttls.enable", starttls);
            props.put("mail.smtp.startssl.enable", startssl);

            session = Session.getDefaultInstance(props);
            transport = session.getTransport("smtp");
            transport.connect(host, port, username, password);
            result.setSuscess(true);
        } catch (final AuthenticationFailedException e) {
            result.setMessage(e.toString());
        } catch (final MessagingException e) {
            result.setMessage(e.toString());
        } catch (final Throwable e) {
            result.setMessage(e.toString());
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (final MessagingException e) {
                }
            }
        }

        return result;
    }

}
