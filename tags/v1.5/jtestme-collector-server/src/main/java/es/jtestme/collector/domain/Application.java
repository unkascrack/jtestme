package es.jtestme.collector.domain;

import es.jtestme.collector.domain.reference.EnvironmentType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@RooJpaActiveRecord
@RooEquals
@RooSerializable
public class Application {

    @NotNull
    @Size(max = 30)
    private String name;

    @Size(max = 250)
    private String description;

    @NotNull
    @Enumerated
    private EnvironmentType environmentType;

    @NotNull
    @Size(max = 250)
    private String url;

    @Size(max = 50)
    private String username;

    @Size(max = 50)
    private String password;

    @NotNull
    @Value("true")
    private boolean startWatching;

    @NotNull
    @Value("false")
    private boolean mailingOk;
    
    @NotNull
    @Value("true")
    private boolean mailingError;

    @NotNull
    @Value("true")
    private boolean mailingNoConnect;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "application")
    private Set<es.jtestme.collector.domain.ApplicationState> states = new HashSet<es.jtestme.collector.domain.ApplicationState>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "application")
    private Set<Owner> owners = new HashSet<Owner>();
    
    public static List<Application> findAllApplicationsByStartWatching() {
        return entityManager().createQuery("SELECT o FROM Application o where o.startWatching = true", Application.class).getResultList();
    }
}
