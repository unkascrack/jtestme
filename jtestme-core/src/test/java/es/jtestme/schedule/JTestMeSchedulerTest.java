package es.jtestme.schedule;

import org.junit.Assert;
import org.junit.Test;

public class JTestMeSchedulerTest {

    @Test
    public void testJTestMeSchedulerNotNull() {
        Assert.assertNotNull(JTestMeScheduler.getInstance());
    }
}
