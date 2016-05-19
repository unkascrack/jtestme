package es.jtestme.verificators.impl;

import java.util.Map;

import javax.crypto.Cipher;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.logger.JTestMeLogger;

public final class JavaCryptographyExtensionVerificator extends AbstractVerificator {

    private static final String ALGORITMO_AES = "AES";
    private static final int MAX_ALLOWED_KEY_LENGTH = 128;

    public JavaCryptographyExtensionVerificator(final Map<String, String> params) {
        super(params);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();
        result.setSuccess(false);
        try {
            final int maxAllowedKeyLength = Cipher.getMaxAllowedKeyLength(ALGORITMO_AES);
            if (maxAllowedKeyLength > MAX_ALLOWED_KEY_LENGTH) {
                result.setSuccess(true);
            } else {
                result.setMessage("Java Cryptography Extension no instalada.");
            }
        } catch (final Throwable cause) {
            JTestMeLogger.warn("JavaCryptographyExtensionVerificator: " + cause.getMessage(), cause);
            result.setMessage("JavaCryptographyExtensionVerificator: no se ha podido cargar Algoritmos JCE Policy.");
            result.setCause(cause);
        }
        return result;
    }
}
