// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.jtestme.collector.domain;

import es.jtestme.collector.domain.ServerState;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect ServerState_Roo_Equals {
    
    public boolean ServerState.equals(Object obj) {
        if (!(obj instanceof ServerState)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ServerState rhs = (ServerState) obj;
        return new EqualsBuilder().append(id, rhs.id).append(stateDate, rhs.stateDate).append(stateType, rhs.stateType).isEquals();
    }
    
    public int ServerState.hashCode() {
        return new HashCodeBuilder().append(id).append(stateDate).append(stateType).toHashCode();
    }
    
}
