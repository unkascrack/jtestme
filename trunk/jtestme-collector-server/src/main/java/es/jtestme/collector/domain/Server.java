package es.jtestme.collector.domain;

import es.jtestme.collector.domain.reference.ServerType;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
public class Server {

    @NotNull
    @Enumerated
    private ServerType serverType;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    @Pattern(regexp = "\\\\b(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    private String url;

    @Size(max = 50)
    private String username;

    @Size(max = 50)
    private String password;

    private String httpProxyHost;

    private String httpProxyPort;

    private String httpsProxyHost;

    private String httpsProxyPort;

    private String proxyUser;

    private String proxyPassword;

    private String trustStorePassword;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ServerState> states = new HashSet<ServerState>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Environment environment;
}
