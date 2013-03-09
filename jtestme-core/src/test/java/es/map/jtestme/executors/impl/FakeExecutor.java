package es.map.jtestme.executors.impl;

import es.map.jtestme.domain.JTestMeResult;
import es.map.jtestme.executors.JTestMeExecutor;

public class FakeExecutor implements JTestMeExecutor {

    public String getName() {
        return null;
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = new JTestMeResult();
        result.setSuscess(true);
        return result;
    }

}
