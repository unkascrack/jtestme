package es.jtestme.collector.domain;

import es.jtestme.collector.domain.reference.EnvironmentType;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
@RooJpaActiveRecord(finders = { "findEnvironmentsByStartWatching" })
public class Environment {

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Enumerated
    private EnvironmentType environmentType;

    @Size(max = 250)
    private String description;

    @NotNull
    @Value("true")
    private boolean startWatching;

    @NotNull
    @Value("true")
    private boolean mailingError;

    @NotNull
    @Value("false")
    private boolean mailingNoConnect;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;    
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "environment")
    private Set<Server> servers = new HashSet<Server>();
}
