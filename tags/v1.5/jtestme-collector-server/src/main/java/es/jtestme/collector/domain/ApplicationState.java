package es.jtestme.collector.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import es.jtestme.collector.domain.reference.StateType;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooEquals
@RooSerializable
public class ApplicationState {

    @NotNull
    @Enumerated
    private StateType stateType;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date stateDate;
    
    @NotNull
    private Integer code;
    
    @NotNull
    private String message;
    
    @Transient
    private String content;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "applicationState")
    private Set<Verificator> verificators = new HashSet<Verificator>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;

    @SuppressWarnings("unchecked")
    public static ApplicationState findLastApplicationState(Long idApplication) {
    	Query query = entityManager().createQuery("SELECT o FROM ApplicationState o WHERE o.application.id = :id ORDER BY o.stateDate DESC");
    	query.setParameter("id", idApplication);
    	query.setFirstResult(0);
    	query.setMaxResults(1);
    	List<ApplicationState> applicationStates = query.getResultList();
    	return CollectionUtils.isNotEmpty(applicationStates) ? applicationStates.get(0) : null;
    }    
}
