package es.jtestme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import es.jtestme.config.ConfigurationBuilder;
import es.jtestme.domain.VerificatorResult;
import es.jtestme.logger.JTestMeLogger;
import es.jtestme.verificators.Verificator;
import es.jtestme.verificators.VerificatorFactory;

public final class JTestMeBuilder {

    private static final JTestMeBuilder INSTANCE = new JTestMeBuilder();
    private static final List<Verificator> verificators = new ArrayList<Verificator>();

    private JTestMeBuilder() {
    }

    public static final JTestMeBuilder getInstance() {
        return INSTANCE;
    }

    /**
     * 
     */
    public void destroy() {
        verificators.clear();
    }

    /**
     * @param configLocation
     * @return
     */
    public void loadVerificators(final String configLocation) {
        final ConfigurationBuilder configuration = ConfigurationBuilder.getInstance();
        final Map<String, Map<String, String>> params = configuration.loadConfiguration(configLocation);
        if (params != null && !params.isEmpty()) {
            final VerificatorFactory verificatorFactory = VerificatorFactory.getInstance();
            for (final Entry<String, Map<String, String>> entry : params.entrySet()) {
                final String name = entry.getKey();
                final Map<String, String> properties = entry.getValue();
                final Verificator verificator = verificatorFactory.loadVerificator(name, properties);
                if (verificator != null) {
                    verificators.add(verificator);
                }
            }
        }
    }

    /**
     * @param verificator
     */
    public void addVerificator(final Verificator verificator) {
        verificators.add(verificator);
    }

    /**
     * @return
     */
    public List<VerificatorResult> runVerificators() {
        final List<VerificatorResult> results = new ArrayList<VerificatorResult>();
        for (final Verificator verificator : verificators) {
            final VerificatorResult result = runVerificator(verificator);
            if (result != null) {
                results.add(result);
            }
        }
        return results;
    }

    /**
     * @param verificatorUid
     * @return
     */
    public VerificatorResult runVerificator(final String verificatorUid) {
        final Verificator verificatorSearch = getVerificator(verificatorUid);
        return verificatorSearch != null ? runVerificator(verificatorSearch) : null;
    }

    /**
     * @param verificator
     * @return
     */
    private VerificatorResult runVerificator(final Verificator verificator) {
        VerificatorResult result = null;
        try {
            result = verificator.execute();
        } catch (final Throwable e) {
            JTestMeLogger.warn("JTestMeBuilder running verificator '" + verificator.getUid() + "': " + e.getMessage(),
                    e);
        }
        return result;
    }

    /**
     * @param verificatorUid
     * @return
     */
    private Verificator getVerificator(final String verificatorUid) {
        Verificator verificatorSearch = null;
        for (final Verificator verificator : verificators) {
            if (verificatorUid != null && verificatorUid.equalsIgnoreCase(verificator.getUid())) {
                verificatorSearch = verificator;
                break;
            }
        }
        return verificatorSearch;
    }
}
