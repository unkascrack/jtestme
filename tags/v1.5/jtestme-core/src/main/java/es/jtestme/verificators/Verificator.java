package es.jtestme.verificators;

import es.jtestme.domain.VerificatorResult;

public interface Verificator {

    /**
     * @return
     */
    String getUid();

    /**
     * @return
     */
    VerificatorResult execute();
}
