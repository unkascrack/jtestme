// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.jtestme.collector.domain;

import es.jtestme.collector.domain.Application;
import es.jtestme.collector.domain.ApplicationDataOnDemand;
import es.jtestme.collector.domain.ApplicationIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ApplicationIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ApplicationIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: ApplicationIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: ApplicationIntegrationTest: @Transactional;
    
    @Autowired
    ApplicationDataOnDemand ApplicationIntegrationTest.dod;
    
    @Test
    public void ApplicationIntegrationTest.testCountApplications() {
        Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());
        long count = Application.countApplications();
        Assert.assertTrue("Counter for 'Application' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void ApplicationIntegrationTest.testFindApplication() {
        Application obj = dod.getRandomApplication();
        Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Application' failed to provide an identifier", id);
        obj = Application.findApplication(id);
        Assert.assertNotNull("Find method for 'Application' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Application' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void ApplicationIntegrationTest.testFindAllApplications() {
        Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());
        long count = Application.countApplications();
        Assert.assertTrue("Too expensive to perform a find all test for 'Application', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Application> result = Application.findAllApplications();
        Assert.assertNotNull("Find all method for 'Application' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Application' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void ApplicationIntegrationTest.testFindApplicationEntries() {
        Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());
        long count = Application.countApplications();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Application> result = Application.findApplicationEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Application' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Application' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void ApplicationIntegrationTest.testFlush() {
        Application obj = dod.getRandomApplication();
        Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Application' failed to provide an identifier", id);
        obj = Application.findApplication(id);
        Assert.assertNotNull("Find method for 'Application' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyApplication(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Application' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ApplicationIntegrationTest.testMergeUpdate() {
        Application obj = dod.getRandomApplication();
        Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Application' failed to provide an identifier", id);
        obj = Application.findApplication(id);
        boolean modified =  dod.modifyApplication(obj);
        Integer currentVersion = obj.getVersion();
        Application merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Application' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ApplicationIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", dod.getRandomApplication());
        Application obj = dod.getNewTransientApplication(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Application' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Application' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Application' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void ApplicationIntegrationTest.testRemove() {
        Application obj = dod.getRandomApplication();
        Assert.assertNotNull("Data on demand for 'Application' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Application' failed to provide an identifier", id);
        obj = Application.findApplication(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Application' with identifier '" + id + "'", Application.findApplication(id));
    }
    
}
