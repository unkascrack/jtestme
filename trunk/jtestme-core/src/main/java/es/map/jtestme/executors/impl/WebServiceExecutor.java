package es.map.jtestme.executors.impl;

import java.util.Map;

import es.map.jtestme.domain.JTestMeResult;

public class WebServiceExecutor extends JTestMeDefaultExecutor {

    public WebServiceExecutor(final Map<String, String> params) {
        super(params);
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();
        return result;
    }

}
