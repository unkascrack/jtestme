package es.map.jtestme.executors;

import es.map.jtestme.domain.JTestMeResult;

public interface JTestMeExecutor {

    /**
     * @return
     */
    String getName();

    /**
     * @param properties
     * @return
     */
    JTestMeResult executeTestMe();
}
