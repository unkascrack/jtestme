// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.jtestme.collector.domain;

import es.jtestme.collector.domain.Server;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect Server_Roo_Equals {
    
    public boolean Server.equals(Object obj) {
        if (!(obj instanceof Server)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Server rhs = (Server) obj;
        return new EqualsBuilder().append(environment, rhs.environment).append(id, rhs.id).append(name, rhs.name).append(password, rhs.password).append(proxyHost, rhs.proxyHost).append(proxyPassword, rhs.proxyPassword).append(proxyPort, rhs.proxyPort).append(proxyUser, rhs.proxyUser).append(serverType, rhs.serverType).append(trustStore, rhs.trustStore).append(trustStorePassword, rhs.trustStorePassword).append(url, rhs.url).append(username, rhs.username).isEquals();
    }
    
    public int Server.hashCode() {
        return new HashCodeBuilder().append(environment).append(id).append(name).append(password).append(proxyHost).append(proxyPassword).append(proxyPort).append(proxyUser).append(serverType).append(trustStore).append(trustStorePassword).append(url).append(username).toHashCode();
    }
    
}
