// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.jtestme.collector.domain;

import es.jtestme.collector.domain.Application;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect Application_Roo_Equals {
    
    public boolean Application.equals(Object obj) {
        if (!(obj instanceof Application)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Application rhs = (Application) obj;
        return new EqualsBuilder().append(description, rhs.description).append(id, rhs.id).append(name, rhs.name).isEquals();
    }
    
    public int Application.hashCode() {
        return new HashCodeBuilder().append(description).append(id).append(name).toHashCode();
    }
    
}
