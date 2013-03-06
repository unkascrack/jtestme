package es.map.jtestme.executors;

import es.map.jtestme.domain.JTestMeResult;

public interface JTestMeExecutor {

    /**
     * @param properties
     * @return
     */
    JTestMeResult executor();
}
