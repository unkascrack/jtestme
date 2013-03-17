package es.jtestme.verificators.custom;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.verificators.Verificator;

public class FakeVerificator implements Verificator {

    public String getUid() {
        return null;
    }

    public VerificatorResult execute() {
        final VerificatorResult result = new VerificatorResult();
        result.setSuscess(true);
        return result;
    }

}
