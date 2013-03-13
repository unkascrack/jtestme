package es.jtestme.executor.custom;

import es.jtestme.domain.JTestMeResult;
import es.jtestme.executors.JTestMeExecutor;
import es.jtestme.executors.JTestMeExecutorType;

public class MyCustomExecutor implements JTestMeExecutor {

    public String getName() {
        return "mycustomexecutor";
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = new JTestMeResult();
        result.setName("mycustomexecutor");
        result.setType(JTestMeExecutorType.CUSTOM.toString());
        result.setSuscess(true);
        return result;
    }

}
