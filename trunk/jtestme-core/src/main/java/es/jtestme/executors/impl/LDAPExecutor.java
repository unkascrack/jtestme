package es.jtestme.executors.impl;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import es.jtestme.domain.JTestMeResult;

public class LDAPExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_URL = "url";
    private static final String PARAM_PRINCIPAL = "principal";
    private static final String PARAM_CREDENTIALS = "credentials";

    private final String url;
    private final String principal;
    private final String credentials;

    public LDAPExecutor(final Map<String, String> params) {
        super(params);
        url = getParamString(PARAM_URL);
        principal = getParamString(PARAM_PRINCIPAL);
        credentials = getParamString(PARAM_CREDENTIALS);
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        final Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        if (url != null) {
            env.put(Context.PROVIDER_URL, url);
        }
        if (principal != null) {
            env.put(Context.SECURITY_PRINCIPAL, principal);
        }
        if (credentials != null) {
            env.put(Context.SECURITY_CREDENTIALS, credentials);
        }

        DirContext context = null;
        try {
            context = new InitialDirContext(env);
            result.setSuscess(true);
        } catch (final NameNotFoundException e) {
            result.setCause(e);
        } catch (final NamingException e) {
            result.setCause(e);
        } catch (final Throwable e) {
            result.setCause(e);
        } finally {
            closeQuietly(context);
        }
        return result;
    }

}