package es.jtestme;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.verificators.custom.FakeVerificator;

public class JTestMeBuilderTest {

    private JTestMeBuilder builder;

    @Before
    public void setUp() {
        builder = JTestMeBuilder.getInstance();
        builder.addVerificator(new FakeVerificator("uid1"));
        builder.addVerificator(new FakeVerificator("uid2"));
        builder.addVerificator(new FakeVerificator("uid3"));
    }

    @After
    public void tearDown() {
        builder.destroy();
    }

    @Test
    public void testBuilderNotNull() {
        Assert.assertNotNull(builder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddVerificatorNull() {
        builder.addVerificator(null);
    }

    @Test
    public void testAddVerificatorNotNull() {
        builder.addVerificator(new FakeVerificator("uid4"));
    }

    @Test
    public void testExecuteVerificatorNull() {
        final VerificatorResult result = builder.executeVerificator(null);
        Assert.assertNull(result);
    }

    @Test
    public void testExecuteVerificatorNotFound() {
        final VerificatorResult result = builder.executeVerificator("verificatornotfound");
        Assert.assertNull(result);
    }

    @Test
    public void testExecuteVerificatorFound() {
        final VerificatorResult result = builder.executeVerificator("uid1");
        Assert.assertNotNull(result);
    }

    @Test
    public void testExecuteVerificatorsNotEmpty() {
        final List<VerificatorResult> results = builder.executeVerificators();
        Assert.assertTrue(results.size() == 3);
    }

    @Test
    public void testExecuteVerificatorsEmpty() {
        builder.destroy();
        final List<VerificatorResult> results = builder.executeVerificators();
        Assert.assertTrue(results.isEmpty());
    }
}
