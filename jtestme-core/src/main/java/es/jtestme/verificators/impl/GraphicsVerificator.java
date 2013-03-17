package es.jtestme.verificators.impl;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.Map;

import es.jtestme.domain.VerificatorResult;

public class GraphicsVerificator extends AbstractVerificator {

    public GraphicsVerificator(final Map<String, String> params) {
        super(params);
    }

    @SuppressWarnings("unused")
    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();
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
