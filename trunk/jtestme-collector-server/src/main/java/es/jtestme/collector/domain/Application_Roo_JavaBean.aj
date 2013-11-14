// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.jtestme.collector.domain;

import es.jtestme.collector.domain.Application;
import es.jtestme.collector.domain.ApplicationState;
import es.jtestme.collector.domain.Owner;
import es.jtestme.collector.domain.reference.EnvironmentType;
import java.util.Set;

privileged aspect Application_Roo_JavaBean {
    
    public String Application.getName() {
        return this.name;
    }
    
    public void Application.setName(String name) {
        this.name = name;
    }
    
    public String Application.getDescription() {
        return this.description;
    }
    
    public void Application.setDescription(String description) {
        this.description = description;
    }
    
    public EnvironmentType Application.getEnvironmentType() {
        return this.environmentType;
    }
    
    public void Application.setEnvironmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
    }
    
    public String Application.getUrl() {
        return this.url;
    }
    
    public void Application.setUrl(String url) {
        this.url = url;
    }
    
    public String Application.getUsername() {
        return this.username;
    }
    
    public void Application.setUsername(String username) {
        this.username = username;
    }
    
    public String Application.getPassword() {
        return this.password;
    }
    
    public void Application.setPassword(String password) {
        this.password = password;
    }
    
    public boolean Application.isStartWatching() {
        return this.startWatching;
    }
    
    public void Application.setStartWatching(boolean startWatching) {
        this.startWatching = startWatching;
    }
    
    public boolean Application.isMailingOk() {
        return this.mailingOk;
    }
    
    public void Application.setMailingOk(boolean mailingOk) {
        this.mailingOk = mailingOk;
    }
    
    public boolean Application.isMailingError() {
        return this.mailingError;
    }
    
    public void Application.setMailingError(boolean mailingError) {
        this.mailingError = mailingError;
    }
    
    public boolean Application.isMailingNoConnect() {
        return this.mailingNoConnect;
    }
    
    public void Application.setMailingNoConnect(boolean mailingNoConnect) {
        this.mailingNoConnect = mailingNoConnect;
    }
    
    public Set<ApplicationState> Application.getStates() {
        return this.states;
    }
    
    public void Application.setStates(Set<ApplicationState> states) {
        this.states = states;
    }
    
    public Set<Owner> Application.getOwners() {
        return this.owners;
    }
    
    public void Application.setOwners(Set<Owner> owners) {
        this.owners = owners;
    }
    
}