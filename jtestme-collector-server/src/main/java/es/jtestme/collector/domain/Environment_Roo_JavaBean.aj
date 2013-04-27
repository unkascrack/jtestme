// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.jtestme.collector.domain;

import es.jtestme.collector.domain.Application;
import es.jtestme.collector.domain.Environment;
import es.jtestme.collector.domain.Server;
import es.jtestme.collector.domain.reference.EnvironmentType;
import java.util.Set;

privileged aspect Environment_Roo_JavaBean {
    
    public String Environment.getName() {
        return this.name;
    }
    
    public void Environment.setName(String name) {
        this.name = name;
    }
    
    public EnvironmentType Environment.getEnvironmentType() {
        return this.environmentType;
    }
    
    public void Environment.setEnvironmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
    }
    
    public String Environment.getDescription() {
        return this.description;
    }
    
    public void Environment.setDescription(String description) {
        this.description = description;
    }
    
    public boolean Environment.isStartWatching() {
        return this.startWatching;
    }
    
    public void Environment.setStartWatching(boolean startWatching) {
        this.startWatching = startWatching;
    }
    
    public boolean Environment.isMailingError() {
        return this.mailingError;
    }
    
    public void Environment.setMailingError(boolean mailingError) {
        this.mailingError = mailingError;
    }
    
    public boolean Environment.isMailingNoConnect() {
        return this.mailingNoConnect;
    }
    
    public void Environment.setMailingNoConnect(boolean mailingNoConnect) {
        this.mailingNoConnect = mailingNoConnect;
    }
    
    public Set<Server> Environment.getServers() {
        return this.servers;
    }
    
    public void Environment.setServers(Set<Server> servers) {
        this.servers = servers;
    }
    
    public Application Environment.getApplication() {
        return this.application;
    }
    
    public void Environment.setApplication(Application application) {
        this.application = application;
    }
    
}
