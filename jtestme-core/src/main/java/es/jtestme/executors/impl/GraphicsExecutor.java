package es.jtestme.executors.impl;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.Map;

import es.jtestme.domain.JTestMeResult;

public class GraphicsExecutor extends JTestMeDefaultExecutor {

    public GraphicsExecutor(final Map<String, String> params) {
        super(params);
    }

    @SuppressWarnings("unused")
    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();
        try {
            final Toolkit tk = Toolkit.getDefaultToolkit();
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsEnvironment.isHeadless();
            Class.forName("sun.awt.X11GraphicsEnvironment");
            result.setSuscess(true);
        } catch (final Throwable e) {
            result.setCause(e);
        }
        return result;
    }

}
