package es.jtestme.logger;

import org.junit.BeforeClass;
import org.junit.Test;

public class JTestMeLoggerTest {

    @BeforeClass
    public static void setUp() {
        JTestMeLogger.setLoggerEnabled(true);
    }

    @Test
    public void testDebug() {
        JTestMeLogger.debug("debug");
    }

    @Test
    public void testInfo() {
        JTestMeLogger.info("info");
    }

    @Test
    public void testWarn() {
        JTestMeLogger.warn("warn");
    }

    @Test
    public void testError() {
        JTestMeLogger.error("error");
    }

    @Test
    public void testLoggerInnerClass() {
        new MyInnerClass();
    }

    class MyInnerClass {

        public MyInnerClass() {
            JTestMeLogger.error("error inner class");
        }
    }
}
