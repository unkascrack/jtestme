package es.jtestme.verificators.impl;

import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.utils.JTestMeUtils;

public final class JNDIVerificator extends AbstractVerificator {

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
        this.factory = getParamString(PARAM_FACTORY);
        this.url = getParamString(PARAM_URL);
        this.pkgs = getParamString(PARAM_PKGS);
        this.lookup = getParamString(PARAM_LOOKUP);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();

        Context context = null;
        try {
            final Properties env = new Properties();
            if (this.factory != null && this.factory.trim().length() > 0) {
                env.put(Context.INITIAL_CONTEXT_FACTORY, this.factory);
            }
            if (this.url != null && this.url.trim().length() > 0) {
                env.put(Context.PROVIDER_URL, this.url);
            }
            if (this.pkgs != null && this.pkgs.trim().length() > 0) {
                env.put(Context.URL_PKG_PREFIXES, this.pkgs);
            }
            context = new InitialContext(env);
            final Object jndiRef = context.lookup(this.lookup);
            if (jndiRef != null) {
                result.setSuccess(true);
            } else {
                result.setMessage("JNDI Reference not found");
            }
        } catch (final NamingException e) {
            result.setCause(e);
        } catch (final Throwable e) {
            result.setCause(e);
        } finally {
            JTestMeUtils.closeQuietly(context);
        }
        return result;
    }
}
