package es.jtestme.schedule;

import org.junit.Test;

import junit.framework.Assert;

public class JTestMeSchedulerTest {

    @Test
    public void testJTestMeSchedulerNotNull() {
        Assert.assertNotNull(JTestMeScheduler.getInstance());
    }
}
