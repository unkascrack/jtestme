// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.jtestme.collector.domain;

import es.jtestme.collector.domain.Application;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Application_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Application.entityManager;
    
    public static final EntityManager Application.entityManager() {
        EntityManager em = new Application().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Application.countApplications() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Application o", Long.class).getSingleResult();
    }
    
    public static List<Application> Application.findAllApplications() {
        return entityManager().createQuery("SELECT o FROM Application o", Application.class).getResultList();
    }
    
    public static Application Application.findApplication(Long id) {
        if (id == null) return null;
        return entityManager().find(Application.class, id);
    }
    
    public static List<Application> Application.findApplicationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Application o", Application.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Application.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Application.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Application attached = Application.findApplication(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Application.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Application.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Application Application.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Application merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
