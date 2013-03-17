package es.jtestme.verificators.custom;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.verificators.Verificator;

public class FakeVerificator implements Verificator {

    private String uid = getClass().getName();

    public FakeVerificator() {
    }

    public FakeVerificator(final String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public VerificatorResult execute() {
        final VerificatorResult result = new VerificatorResult();
        result.setSuscess(true);
        return result;
    }
}
