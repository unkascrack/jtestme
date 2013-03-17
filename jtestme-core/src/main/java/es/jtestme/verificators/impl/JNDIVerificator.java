package es.jtestme.verificators.impl;

import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import es.jtestme.domain.VerificatorResult;

public class JNDIVerificator extends AbstractVerificator {

    private static final String PARAM_FACTORY = "factory";
    private static final String PARAM_URL = "url";
    private static final String PARAM_PKGS = "pkgs";
    private static final String PARAM_LOOKUP = "lookup";

    private final String factory;
    private final String url;
    private final String pkgs;
    private final String lookup;

    public JNDIVerificator(final Map<String, String> params) {
        super(params);
        factory = getParamString(PARAM_FACTORY);
        url = getParamString(PARAM_URL);
        pkgs = getParamString(PARAM_PKGS);
        lookup = getParamString(PARAM_LOOKUP);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();

        Context context = null;
        try {
            final Properties env = new Properties();
            if (factory != null && factory.trim().length() > 0) {
                env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
            }
            if (url != null && url.trim().length() > 0) {
                env.put(Context.PROVIDER_URL, url);
            }
            if (pkgs != null && pkgs.trim().length() > 0) {
                env.put(Context.URL_PKG_PREFIXES, pkgs);
            }
            context = new InitialContext(env);
            final Object jndiRef = context.lookup(lookup);
            if (jndiRef != null) {
                result.setSuscess(true);
            } else {
                result.setMessage("JNDI Reference not found");
            }
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
