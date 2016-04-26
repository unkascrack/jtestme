package es.jtestme.verificators.impl;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.Map;

import es.jtestme.domain.VerificatorResult;

public final class GraphicsVerificator extends AbstractVerificator {

    public GraphicsVerificator(final Map<String, String> params) {
        super(params);
    }

    @SuppressWarnings("unused")
    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();
        try {
            // final String graphicClass =
            // System.getProperty("java.awt.graphicsenv");
            final Toolkit tk = Toolkit.getDefaultToolkit();
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            result.setSuccess(true);
        } catch (final Throwable e) {
            result.setCause(e);
        }
        return result;
    }

}
