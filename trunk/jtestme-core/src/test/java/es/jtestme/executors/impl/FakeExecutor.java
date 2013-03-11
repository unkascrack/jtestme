package es.jtestme.executors.impl;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.executors.JTestMeExecutor;

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
