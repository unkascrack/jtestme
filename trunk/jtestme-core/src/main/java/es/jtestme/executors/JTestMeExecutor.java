package es.jtestme.executors;

import es.jtestme.domain.JTestMeResult;

public interface JTestMeExecutor {

    /**
     * @return
     */
    String getName();

    /**
     * @return
     */
    JTestMeResult executeTestMe();
}
