package es.jtestme.logger;

import org.junit.BeforeClass;
import org.junit.Test;

public class JTestMeLoggerTest {

    @BeforeClass
    public static void setUp() {
        JTestMeLogger.loggerEnabled(true);
    }

    @Test
    public void testDebug() {
        JTestMeLogger.debug("");
    }

    @Test
    public void testInfo() {
        JTestMeLogger.info("");
    }
}
