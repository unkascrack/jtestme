package es.jtestme.schedule;

import junit.framework.Assert;

import org.junit.Test;

public class JTestMeSchedulerTest {

    @Test
    public void testJTestMeSchedulerNotNull() {
        Assert.assertNotNull(JTestMeScheduler.getInstance());
    }
}
