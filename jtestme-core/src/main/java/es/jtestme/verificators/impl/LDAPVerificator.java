package es.jtestme.verificators.impl;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.utils.JTestMeUtils;

public class LDAPVerificator extends AbstractVerificator {

    private static final String PARAM_URL = "url";
    private static final String PARAM_PRINCIPAL = "principal";
    private static final String PARAM_CREDENTIALS = "credentials";
    private static final String PARAM_AUTHENTICATION = "authentication";
    private static final String PARAM_PROTOCOL = "protocol";
    private static final String PARAM_TRUSTSTORE = "truststore";
    private static final String PARAM_TRUSTSTOREPASSWORD = "truststorepassword";

    private final String authentication;
    private final String url;
    private final String principal;
    private final String credentials;
    private final String protocol;
    private final String trustStore;
    private final String trustStorePassword;

    public LDAPVerificator(final Map<String, String> params) {
        super(params);
        url = getParamString(PARAM_URL);
        principal = getParamString(PARAM_PRINCIPAL);
        credentials = getParamString(PARAM_CREDENTIALS);
        authentication = getParamString(PARAM_AUTHENTICATION, "simple");
        protocol = getParamString(PARAM_PROTOCOL);
        trustStore = getParamString(PARAM_TRUSTSTORE);
        trustStorePassword = getParamString(PARAM_TRUSTSTOREPASSWORD);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();

        final Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, authentication);

        if (url != null) {
            env.put(Context.PROVIDER_URL, url);
        }
        if (principal != null) {
            env.put(Context.SECURITY_PRINCIPAL, principal);
        }
        if (credentials != null) {
            env.put(Context.SECURITY_CREDENTIALS, credentials);
        }
        if (protocol != null) {
            env.put(Context.SECURITY_PROTOCOL, protocol);
        }

        loadTrustStore(trustStore, trustStorePassword);
        DirContext context = null;
        try {
            context = new InitialDirContext(env);
            result.setSuccess(true);
        } catch (final NameNotFoundException e) {
            result.setCause(e);
        } catch (final NamingException e) {
            result.setCause(e);
        } catch (final Throwable e) {
            result.setCause(e);
        } finally {
            relaseTrustStore(trustStore, trustStorePassword);
            JTestMeUtils.closeQuietly(context);
        }
        return result;
    }
}
