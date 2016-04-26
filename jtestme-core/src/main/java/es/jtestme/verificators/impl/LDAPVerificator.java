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

public final class LDAPVerificator extends AbstractVerificator {

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
        this.url = getParamString(PARAM_URL);
        this.principal = getParamString(PARAM_PRINCIPAL);
        this.credentials = getParamString(PARAM_CREDENTIALS);
        this.authentication = getParamString(PARAM_AUTHENTICATION, "simple");
        this.protocol = getParamString(PARAM_PROTOCOL);
        this.trustStore = getParamString(PARAM_TRUSTSTORE);
        this.trustStorePassword = getParamString(PARAM_TRUSTSTOREPASSWORD);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();

        final Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, this.authentication);

        if (this.url != null) {
            env.put(Context.PROVIDER_URL, this.url);
        }
        if (this.principal != null) {
            env.put(Context.SECURITY_PRINCIPAL, this.principal);
        }
        if (this.credentials != null) {
            env.put(Context.SECURITY_CREDENTIALS, this.credentials);
        }
        if (this.protocol != null) {
            env.put(Context.SECURITY_PROTOCOL, this.protocol);
        }

        loadTrustStore(this.trustStore, this.trustStorePassword);
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
            relaseTrustStore(this.trustStore, this.trustStorePassword);
            JTestMeUtils.closeQuietly(context);
        }
        return result;
    }
}
