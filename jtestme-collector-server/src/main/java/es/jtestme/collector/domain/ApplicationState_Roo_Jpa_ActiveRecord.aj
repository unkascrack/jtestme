// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.jtestme.collector.domain;

import es.jtestme.collector.domain.ApplicationState;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ApplicationState_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager ApplicationState.entityManager;
    
    public static final EntityManager ApplicationState.entityManager() {
        EntityManager em = new ApplicationState().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ApplicationState.countApplicationStates() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ApplicationState o", Long.class).getSingleResult();
    }
    
    public static List<ApplicationState> ApplicationState.findAllApplicationStates() {
        return entityManager().createQuery("SELECT o FROM ApplicationState o", ApplicationState.class).getResultList();
    }
    
    public static ApplicationState ApplicationState.findApplicationState(Long id) {
        if (id == null) return null;
        return entityManager().find(ApplicationState.class, id);
    }
    
    public static List<ApplicationState> ApplicationState.findApplicationStateEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ApplicationState o", ApplicationState.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void ApplicationState.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ApplicationState.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ApplicationState attached = ApplicationState.findApplicationState(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ApplicationState.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ApplicationState.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ApplicationState ApplicationState.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ApplicationState merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
