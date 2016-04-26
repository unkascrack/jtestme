package es.jtestme.verificators.impl;

import java.util.Map;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import es.jtestme.domain.VerificatorResult;

public final class SMTPVerificator extends AbstractVerificator {

    private static final int DEFAULT_SMTP_PORT = 25;

    private static final String PARAM_HOST = "host";
    private static final String PARAM_PORT = "port";
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_AUTH = "auth";
    private static final String PARAM_STARTTLS = "starttls";
    private static final String PARAM_STARTSSL = "startssl";

    private final String host;
    private final Integer port;
    private final String username;
    private final String password;
    private boolean auth = false;
    private boolean starttls = false;
    private boolean startssl = false;

    public SMTPVerificator(final Map<String, String> params) {
        super(params);
        this.host = getParamString(PARAM_HOST);
        this.port = getParamInteger(PARAM_PORT, DEFAULT_SMTP_PORT);
        this.username = getParamString(PARAM_USERNAME);
        this.password = getParamString(PARAM_PASSWORD);
        this.auth = getParamBoolean(PARAM_AUTH, false);
        this.starttls = getParamBoolean(PARAM_STARTTLS, false);
        this.startssl = getParamBoolean(PARAM_STARTSSL, false);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();

        Session session = null;
        Transport transport = null;
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", this.auth);
            props.put("mail.smtp.starttls.enable", this.starttls);
            props.put("mail.smtp.startssl.enable", this.startssl);

            session = Session.getDefaultInstance(props);
            transport = session.getTransport("smtp");
            transport.connect(this.host, this.port, this.username, this.password);
            result.setSuccess(true);
        } catch (final AuthenticationFailedException e) {
            result.setCause(e);
        } catch (final MessagingException e) {
            result.setCause(e);
        } catch (final Throwable e) {
            result.setCause(e);
        } finally {
            closeQuietly(transport);
        }

        return result;
    }

    private void closeQuietly(final Transport transport) {
        if (transport != null) {
            try {
                transport.close();
            } catch (final MessagingException e) {
            }
        }
    }

}
