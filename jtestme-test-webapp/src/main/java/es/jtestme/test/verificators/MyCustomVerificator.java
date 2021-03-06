package es.jtestme.test.verificators;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.verificators.Verificator;
import es.jtestme.verificators.VerificatorType;

public class MyCustomVerificator implements Verificator {

    public String getUid() {
        return "mycustomverificator";
    }

    public VerificatorResult execute() {
        final VerificatorResult result = new VerificatorResult();
        result.setName("mycustomverificator");
        result.setType(VerificatorType.CUSTOM.toString());
        result.setSuccess(true);
        return result;
    }

}
