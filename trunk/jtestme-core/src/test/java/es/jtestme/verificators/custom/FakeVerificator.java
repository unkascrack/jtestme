package es.jtestme.verificators.custom;

import java.util.Map;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.verificators.Verificator;

public class FakeVerificator implements Verificator {

    private String uid = getClass().getName();
    private Map<String, String> params;

    public FakeVerificator() {
    }

    public FakeVerificator(final String uid) {
        this(uid, null);
    }

    public FakeVerificator(final Map<String, String> params) {
        this(null, params);
    }

    public FakeVerificator(final String uid, final Map<String, String> params) {
        this.uid = uid;
        this.params = params;
    }

    public String getUid() {
        return uid;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public VerificatorResult execute() {
        final VerificatorResult result = new VerificatorResult();
        result.setSuccess(true);
        return result;
    }
}
